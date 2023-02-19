package co.luminositylabs.testing;

public class JavaHelloWorld {

    /** The prefix for the greeting. */
    public static final String GREETING_PREFIX = "Hello ";

    public String sayHello(final String name) {
        final String greeting = GREETING_PREFIX + name;
        System.out.println(greeting);
        return greeting;
    }

}
