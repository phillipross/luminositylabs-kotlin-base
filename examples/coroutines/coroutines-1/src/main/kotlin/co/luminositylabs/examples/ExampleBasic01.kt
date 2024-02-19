package co.luminositylabs.examples

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = ExampleBasicNonBlocking().nonBlockingHelloWorld()

@SuppressFBWarnings("NP_PARAMETER_MUST_BE_NONNULL_BUT_MARKED_AS_NULLABLE")
class ExampleBasicNonBlocking {
    fun nonBlockingHelloWorld() {
        runBlocking { // this: CoroutinesScope
            launch { // launch a new coroutine and continue
                delay(1000L) // non-blocking delay for 1 second (default unit is ms)
                println("World!") // printer after delay
            }
            println("Hello") // main coroutine continues while a previous one is delayed
        }
    }
}
