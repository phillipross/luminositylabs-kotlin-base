package co.luminositylabs.testing

class KotlinHelloWorld {

    val greetingPrefix = "Hello "

    fun sayHello(name: String): String {
        val greeting = "$greetingPrefix$name"
        println(greeting)
        return greeting
    }
}
