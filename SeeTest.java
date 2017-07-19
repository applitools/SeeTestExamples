package <set your test package>;
import org.junit.*;
import static org.junit.Assert.*;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.selenium.Eyes;

//package <set your test package>;
import com.experitest.selenium.*;

import com.experitest.appium.SeeTestAndroidDriver;

public class SeeTest {
    private String host = "localhost";
    private int port = 8889;
    private String projectBaseDirectory = "/Users/justin/workspace/project2";
    protected MobileWebDriver driver = null;
    private Eyes eyes = new Eyes();

    @Before
    public void setUp(){
        driver = new MobileWebDriver(host, port, projectBaseDirectory, "xml", "reports", "simple_test1");
        eyes.setApiKey("9RkMajXrzS1Zu110oTWQps102CHiPRPmeyND99E9iL0G7yAc110");
        eyes.open(driver, "Login Example", "SeeTest");
    }

    @Test
    public void testsimple_test1(){
        driver.setDevice("adb:R1 HD");
        driver.application("com.experitest.ExperiBank/.LoginActivity").launch(true, true);
        driver.device().sendText("hello");
        driver.findElement(new ByObject("default", "Password")).sendKeys("password");
        driver.findElement(new ByObject("default", "Login")).click();
        driver.findElement(new ByObject("default", "Close")).click();
        eyes.checkWindow("Login");
        TestResults results = eyes.close();
        assertEquals(true, results.isPassed());
    }

    @After
    public void tearDown(){
        // Generates a report of the test case.
        // For more information - https://docs.experitest.com/display/public/SA/Report+Of+Executed+Test
        driver.generateReport(false);
        // Releases the client so that other clients can approach the agent in the near future.
        driver.releaseClient();
        eyes.abortIfNotClosed();
    }
}