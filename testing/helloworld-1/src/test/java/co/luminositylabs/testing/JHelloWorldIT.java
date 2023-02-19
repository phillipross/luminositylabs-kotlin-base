package co.luminositylabs.testing;


import org.testng.Assert;
import org.testng.annotations.Test;


public class JHelloWorldIT {

    @Test
    public void test() {
        final String testName = "Boba["+ getClass().getSimpleName() + "]";
        Assert.assertEquals("Hello " + testName, new JavaHelloWorld().sayHello(testName));
    }

}
