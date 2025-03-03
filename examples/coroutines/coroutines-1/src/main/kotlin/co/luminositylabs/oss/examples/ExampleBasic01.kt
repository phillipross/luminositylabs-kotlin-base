package co.luminositylabs.oss.examples

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

val logger = KotlinLogging.logger {}

fun main() = ExampleBasicNonBlocking().nonBlockingHelloWorld()

class ExampleBasicNonBlocking {
    fun nonBlockingHelloWorld() {
        runBlocking {
            // this: CoroutinesScope

            // launch a new coroutine and continue
            launch {
                delay(1000L) // non-blocking delay for 1 second (default unit is ms)
                logger.debug { "World!" } // printer after delay
            }

            logger.debug { "Hello" } // main coroutine continues while a previous one is delayed
        }
    }
}
