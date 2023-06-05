package co.luminositylabs.examples

import com.google.gson.GsonBuilder
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class BookTest {

    @Test
    fun `test data class to json serialization`() {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val books = mutableListOf(
            Book(UUID.randomUUID(), "Book 1", "Alan Alanson"),
            Book(UUID.randomUUID(), "Book 2", "Ben Benson"),
            Book(UUID.randomUUID(), "Book 3", "Carl Carlson"),
            Book(UUID.randomUUID(), "Book 4", "David Davidson"),
            Book(UUID.randomUUID(), "Book 5", "Edward Edwardson"),
        )
        val jsonBooks = books.asSequence()
            .map { gson.toJson(it) }
            // .onEach { println(it) }
            .toList()
        val objectBooks = jsonBooks.asSequence()
            .map { gson.fromJson(it, Book::class.java) }
            // .onEach { println(it) }
            .toList()
        assertEquals(books, objectBooks)
    }
}
