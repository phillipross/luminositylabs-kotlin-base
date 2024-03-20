package co.luminositylabs.oss.testing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple hello world implemented directly in java.
 */
public class JavaHelloWorld {

    /** The static logger instance. */
    private static final Logger logger = LoggerFactory.getLogger(JavaHelloWorld.class);

    /** The prefix for the greeting. */
    public static final String GREETING_PREFIX = "Hello ";

    /** Default no-args constructor. */
    protected JavaHelloWorld() {
    }

    /**
     * Print and return a greeting for a specified name.
     *
     * @param name the name for the greeting
     * @return the greeting
     */
    public String sayHello(final String name) {
        final String greeting = GREETING_PREFIX + name;
        logger.debug(greeting);
        return greeting;
    }
}
