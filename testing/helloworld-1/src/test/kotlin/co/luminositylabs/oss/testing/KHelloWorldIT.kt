package co.luminositylabs.oss.testing

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.test.Test
import kotlin.test.assertEquals

private val logger = KotlinLogging.logger {}

class KHelloWorldIT {
    @Test
    fun `Integration Test`() {
        logger.trace { "integration test" }
        val testName = "Boba"
        assertEquals("Hello $testName", KotlinHelloWorld().sayHello(testName))
    }
}
