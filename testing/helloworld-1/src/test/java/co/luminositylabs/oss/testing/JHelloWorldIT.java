package co.luminositylabs.oss.testing;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;


public class JHelloWorldIT {

    /** The static logger instance. */
    private static final Logger logger = LoggerFactory.getLogger(JHelloWorldIT.class);

    @Test
    public void integrationTest() {
        logger.trace("integration test");
        final String testName = "Boba";
        Assert.assertEquals("Hello " + testName, new JavaHelloWorld().sayHello(testName));
    }

}
