package co.luminositylabs.oss.examples.jackson.polymorphic

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

data class GameModels(
    @field:JsonValue
    val model: ModelValue,
) {
    class ModelValueDeserializerStreaming : JsonDeserializer<ModelValue>() {
        override fun deserialize(
            p: JsonParser,
            ctxt: DeserializationContext,
        ): ModelValue {
            var result: ModelValue? = null

            // Iterate through the tokens of the object
            while (p.nextToken() != JsonToken.END_OBJECT) {
                println("token: ${p.currentToken}")
                val fieldName = p.currentName()
                println("fieldName: $fieldName")
                if (fieldName == "model") {
                    p.nextToken() // Move to the value
                    println("token2: ${p.currentToken}")

                    result =
                        when (p.currentToken) {
                            JsonToken.VALUE_STRING -> {
                                ModelValue.Single(p.text)
                            }

                            JsonToken.START_ARRAY -> {
                                val items = mutableListOf<String>()
                                while (p.nextToken() != JsonToken.END_ARRAY) {
                                    items.add(p.text)
                                }
                                ModelValue.Multi(items)
                            }

                            else -> {
                                throw MismatchedInputException.from(p, ModelValue::class.java, "Expected string or array")
                            }
                        }
                } else {
                    p.skipChildren() // Skip other fields for performance
                }
            }

            return result ?: throw MismatchedInputException.from(p, ModelValue::class.java, "Missing field 'model'")
        }
    }

    // Custom deserializer for polymorphic handling
    class ModelValueDeserializerOrig : JsonDeserializer<ModelValue>() {
        override fun deserialize(
            p: JsonParser,
            ctxt: DeserializationContext,
        ): ModelValue {
            val objectNode = p.codec.readTree<JsonNode>(p)
            println("Deserializing: $objectNode / ${objectNode.nodeType}")
            val valueNode = objectNode["model"]
            println("Deserializing: $valueNode / ${valueNode.isTextual}")
            return when {
                valueNode.isTextual -> ModelValue.Single(valueNode.asText())
                valueNode.isArray -> ModelValue.Multi(valueNode.map { it.asText() }.toList())
                else -> throw MismatchedInputException.from(p, ModelValue::class.java, "Expected string or array")
            }
        }
    }

    sealed class ModelValue {
        data class Single(
            val model: String,
        ) : ModelValue()

        data class Multi(
            val model: List<String>,
        ) : ModelValue()

        companion object {
            @JvmStatic
            @JsonCreator
            fun fromJson(value: Any?): ModelValue =
                when (value) {
                    is String -> Single(value)
                    is List<*> -> Multi(value.map { it.toString() })
                    else -> throw IllegalArgumentException("Unsupported type")
                }
        }
    }
}

fun main() {
    // Bypass tge beed for @JsonCreator by explicitly registering the deserializser with the object mapper
    val module = SimpleModule()
    // module.addDeserializer(GameModels.ModelValue::class.java, GameModels.ModelValueDeserializerOrig())
    module.addDeserializer(GameModels.ModelValue::class.java, GameModels.ModelValueDeserializerStreaming())

    val mapper = jacksonObjectMapper().registerKotlinModule().registerModule(module)

    // Serialize single model
    val singleJson = mapper.writeValueAsString(GameModels(GameModels.ModelValue.Single("mario5")))
    println("Single: $singleJson")

    // Serialize multiple models
    val multiJson =
        mapper.writeValueAsString(
            GameModels(GameModels.ModelValue.Multi(listOf("mario1", "mario2", "mario3"))),
        )
    println("Multi: $multiJson")

    // Deserialize single model
    println("deserialize single model")
    val singleDeserialized =
        mapper.readValue(
            """{"model":"mario5"}""",
            GameModels::class.java,
        )
    println("Deserialized Single: ${singleDeserialized.model}")

    // Deserialize multiple models
    println("deserialize multiple models")
    val multiDeserialized =
        mapper.readValue(
            """{"model":["mario1","mario2","mario3"]}""",
            GameModels::class.java,
        )
    println("Deserialized Multi: ${multiDeserialized.model}")
}
