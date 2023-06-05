package co.luminositylabs.examples

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class BookTest {

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun `test data class to json serialization`() {
        val moshi = Moshi.Builder()
            .add(UUIDAdapter)
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val bookAdapter = moshi.adapter<Book>()
        val books = mutableListOf(
            Book(UUID.randomUUID(), "Book 1", "Alan Alanson"),
            Book(UUID.randomUUID(), "Book 2", "Ben Benson"),
            Book(UUID.randomUUID(), "Book 3", "Carl Carlson"),
            Book(UUID.randomUUID(), "Book 4", "David Davidson"),
            Book(UUID.randomUUID(), "Book 5", "Edward Edwardson"),
        )
        val jsonBooks = books.asSequence()
            .map { bookAdapter.toJson(it) }
            // .onEach { println(it) }
            .toList()
        val objectBooks = jsonBooks.asSequence()
            .map { bookAdapter.fromJson(it) }
            // .onEach { println(it) }
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
