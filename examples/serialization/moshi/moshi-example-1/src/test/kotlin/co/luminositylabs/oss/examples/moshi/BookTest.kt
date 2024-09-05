package co.luminositylabs.oss.examples.moshi

import co.luminositylabs.oss.examples.ExampleBook
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

val logger = KotlinLogging.logger { }

class BookTest {
    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun `Test data class to json serialization`() {
        logger.trace { "testing data class to json serialization" }
        val moshi =
            Moshi
                .Builder()
                .add(UUIDAdapter)
                .addLast(KotlinJsonAdapterFactory())
                .build()
        val bookAdapter = moshi.adapter<ExampleBook>()
        val books =
            mutableListOf(
                ExampleBook(UUID.randomUUID(), "Book 1", "Alan Alanson"),
                ExampleBook(UUID.randomUUID(), "Book 2", "Ben Benson"),
                ExampleBook(UUID.randomUUID(), "Book 3", "Carl Carlson"),
                ExampleBook(UUID.randomUUID(), "Book 4", "David Davidson"),
                ExampleBook(UUID.randomUUID(), "Book 5", "Edward Edwardson"),
            )
        val jsonBooks =
            books
                .asSequence()
                .map { bookAdapter.toJson(it) }
                .onEach { logger.debug { it } }
                .toList()
        val objectBooks =
            jsonBooks
                .asSequence()
                .map { bookAdapter.fromJson(it) }
                .onEach { logger.debug { it } }
                .toList()
        assertEquals(books, objectBooks)
    }
}

object UUIDAdapter {
    @FromJson
    fun fromJson(string: String) = UUID.fromString(string)

    @ToJson
    fun toJson(uuid: UUID) = uuid.toString()
}
