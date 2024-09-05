package co.luminositylabs.oss.examples

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

val logger = KotlinLogging.logger {}

fun main() = ExampleBasicNonBlocking().nonBlockingHelloWorld()

@SuppressFBWarnings("NP_PARAMETER_MUST_BE_NONNULL_BUT_MARKED_AS_NULLABLE")
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
