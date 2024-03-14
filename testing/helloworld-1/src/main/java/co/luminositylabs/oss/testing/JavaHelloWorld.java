package co.luminositylabs.oss.testing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaHelloWorld {

    /** The static logger instance. */
    private static final Logger logger = LoggerFactory.getLogger(JavaHelloWorld.class);

    /** The prefix for the greeting. */
    public static final String GREETING_PREFIX = "Hello ";

    public String sayHello(final String name) {
        final String greeting = GREETING_PREFIX + name;
        logger.debug(greeting);
        return greeting;
    }

}
