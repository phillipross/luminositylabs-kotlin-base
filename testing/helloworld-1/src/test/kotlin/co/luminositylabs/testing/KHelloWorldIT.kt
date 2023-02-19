package co.luminositylabs.testing

import kotlin.test.Test
import kotlin.test.assertEquals

class KHelloWorldIT {

    @Test
    fun someTest() {
        val testName = "Boba[${javaClass.simpleName}]"
        assertEquals("Hello $testName", KotlinHelloWorld().sayHello(testName))
    }
}
