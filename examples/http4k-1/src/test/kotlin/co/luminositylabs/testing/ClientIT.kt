package co.luminositylabs.testing

import org.http4k.client.JavaHttpClient
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import java.net.http.HttpClient
import kotlin.test.Test
import kotlin.test.assertEquals

class ClientIT {
    @Test
    fun `test http client requests`() {
        val client: HttpHandler =
            JavaHttpClient(
                HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build(),
            )
        setOf(
            "https://www.http4k.org/",
            "https://www.http4k.org/documentation/",
            "https://www.http4k.org/changelog/",
            "https://www.http4k.org/support/",
            "https://www.http4k.org/faq/",
            "https://github.com/http4k/http4k",
        ).associateWith { Request(Method.GET, it) }
            .mapValues { client(it.value) }
            .mapValues { it.value.status }
            .forEach { assertEquals(200, it.value.code) }
    }
}
