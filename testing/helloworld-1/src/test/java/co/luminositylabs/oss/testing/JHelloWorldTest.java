package co.luminositylabs.oss.testing;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;


public class JHelloWorldTest {

    /** The static logger instance. */
    private static final Logger logger = LoggerFactory.getLogger(JHelloWorldTest.class);

    @Test
    public void unitTest() {
        logger.trace("unit test");
        final String testName = "Boba";
        Assert.assertEquals("Hello " + testName, new JavaHelloWorld().sayHello(testName));
    }

}
