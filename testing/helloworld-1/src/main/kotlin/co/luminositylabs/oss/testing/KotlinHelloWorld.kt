package co.luminositylabs.oss.testing

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

class KotlinHelloWorld {
    val greetingPrefix = "Hello "

    fun sayHello(name: String): String {
        val greeting = "$greetingPrefix$name"
        logger.debug { greeting }
        return greeting
    }
}
