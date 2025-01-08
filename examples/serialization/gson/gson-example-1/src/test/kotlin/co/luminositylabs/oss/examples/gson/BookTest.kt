package co.luminositylabs.oss.examples.gson

import co.luminositylabs.oss.examples.ExampleBook
import com.google.gson.GsonBuilder
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

val logger = KotlinLogging.logger { }

class BookTest {
    @Test
    fun `Test data class to json serialization`() {
        logger.trace { "testing data class to json serialization" }
        val gson = GsonBuilder().setPrettyPrinting().create()
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
                .map { gson.toJson(it) }
                .onEach { logger.debug { it } }
                .toList()
        val objectBooks =
            jsonBooks
                .asSequence()
                .map { gson.fromJson(it, ExampleBook::class.java) }
                .onEach { logger.debug { it } }
                .toList()
        assertEquals(books, objectBooks)
    }
}
