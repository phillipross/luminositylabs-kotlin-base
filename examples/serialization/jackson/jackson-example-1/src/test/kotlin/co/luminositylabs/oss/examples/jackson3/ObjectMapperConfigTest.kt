package co.luminositylabs.oss.examples.jackson3

import co.luminositylabs.oss.examples.ExampleBook
import co.luminositylabs.oss.examples.jackson.logger
import com.fasterxml.jackson.annotation.JsonInclude
import tools.jackson.databind.JsonNode
import tools.jackson.databind.PropertyNamingStrategies
import tools.jackson.databind.SerializationFeature
import tools.jackson.module.kotlin.jacksonMapperBuilder
import tools.jackson.module.kotlin.jacksonObjectMapper
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class ObjectMapperConfigTest {
    private val defaultMapper = jacksonObjectMapper()
    private val books =
        listOf(
            ExampleBook(UUID.randomUUID(), "Book 1", "Alan Alanson"),
            ExampleBook(UUID.randomUUID(), "Book 2", "Ben Benson"),
            ExampleBook(UUID.randomUUID(), "Book 3", "Carl Carlson"),
            ExampleBook(UUID.randomUUID(), "Book 4", "David Davidson"),
            ExampleBook(UUID.randomUUID(), "Book 5", "Edward Edwardson"),
        )
    private val defaultJson = defaultMapper.writeValueAsString(books)

    @Test
    fun `Test Object Mapper Serialization Feature Indent Output`() {
        logger.trace { "Test Object Mapper Serialization Feature Indent Output" }
        val indentMapper = jacksonMapperBuilder().enable(SerializationFeature.INDENT_OUTPUT).build()
        assertEquals(defaultJson.lines().size, 1, "default json should just one line")
        val indentJsonString = indentMapper.writeValueAsString(books)
        val numberOfPropertiesInClass = 4
        val extraLinesPerClass = 1
        val extraLines = 1
        assertEquals(
            indentJsonString.lines().size,
            (books.size * (numberOfPropertiesInClass + extraLinesPerClass)) + extraLines,
            "indent result should be multiple lines",
        )
        assertNotEquals(
            defaultJson,
            indentJsonString,
            "defaultJson should be a different string than indentJson",
        )
    }

    @Test
    fun `Test Object Mapper Include Not Null`() {
        logger.trace { "Test Object Mapper Include Not Null" }
        val includeNonNullMapper =
            jacksonMapperBuilder()
                .changeDefaultPropertyInclusion { value ->
                    value
                        .withContentInclusion(JsonInclude.Include.NON_NULL)
                        .withValueInclusion(JsonInclude.Include.NON_NULL)
                }.build()
        // With the default mapper, the properties that are null should be written out
        defaultMapper.readTree(defaultJson).apply {
            assertIsArray()
            forEach {
                it.assertIsObject()
                it.assertHasProperty("fiction")
                it.get("fiction").assertIsNull()
            }
        }
        // With the include non-null mapper, the properties that are null should NOT be written out
        val includeNonNullJsonString = includeNonNullMapper.writeValueAsString(books)
        includeNonNullMapper.readTree(includeNonNullJsonString).apply {
            assertIsArray()
            forEach {
                it.assertIsObject()
                it.assertDoesNotHaveProperty("fiction")
            }
        }
    }

    @Test
    fun `Test Object Mapper Property Naming Strategy Snake Case`() {
        logger.trace { "Test Object Mapper Property Naming Strategy Snake Case" }
        val snakeCaseMapper = jacksonMapperBuilder().propertyNamingStrategy(PropertyNamingStrategies.SnakeCaseStrategy()).build()
        defaultMapper.readTree(defaultJson).apply {
            assertIsArray()
            forEach {
                it.assertIsObject()
                it.assertHasProperty("authorName")
                it.assertDoesNotHaveProperty("author_name")
            }
        }

        val snakeCaseJsonString = snakeCaseMapper.writeValueAsString(books)
        snakeCaseMapper.readTree(snakeCaseJsonString).apply {
            assertIsArray()
            forEach {
                it.assertIsObject()
                it.assertHasProperty("author_name")
                it.assertDoesNotHaveProperty("authorName")
            }
        }
    }

    private fun JsonNode.assertHasProperty(name: String) {
        assertTrue(has(name), "Expected property '$name' to be present, but was missing. Node: $this")
    }

    private fun JsonNode.assertDoesNotHaveProperty(name: String) {
        assertFalse(has(name), "Expected property '$name' to be absent, but was present. Node: $this")
    }

    private fun JsonNode.assertIsArray() {
        assertTrue(isArray, "Expected node to be a JSON array, but was $nodeType")
    }

    private fun JsonNode.assertIsObject() {
        assertTrue(isObject, "Expected node to be a JSON object, but was $nodeType")
    }

    private fun JsonNode.assertIsNull() {
        assertTrue(isNull, "Expected node to be a JSON null, but was $nodeType")
    }

    private fun JsonNode.assertIsNotNull() {
        assertFalse(isNull, "Expected node to be a JSON null, but was $nodeType")
    }
}
