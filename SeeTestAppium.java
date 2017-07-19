package <set your test package>;
import com.applitools.eyes.FileLogger;
import com.applitools.eyes.StdoutLogHandler;
import com.applitools.eyes.TestResults;
import com.experitest.appium.*;
import io.appium.java_client.DriverMobileCommand;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;
import org.junit.*;
import java.net.URL;
import java.net.MalformedURLException;
import com.applitools.eyes.selenium.Eyes;

import static org.junit.Assert.assertEquals;

public class SeeTestAppium {
    private String host = "localhost";
    private int port = 8889;
    private String reportDirectory = "reports";
    private String reportFormat = "xml";
    private String testName = "Untitled";
    protected SeeTestAndroidDriver<SeeTestAndroidElement> driver = null;
    private Eyes eyes = new Eyes();

    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(SeeTestCapabilityType.REPORT_DIRECTORY, reportDirectory);
        dc.setCapability(SeeTestCapabilityType.REPORT_FORMAT, reportFormat);
        dc.setCapability(SeeTestCapabilityType.TEST_NAME, testName);
        dc.setCapability(MobileCapabilityType.UDID, "IBZ5AQMBCY7DHASK");
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
        driver = new SeeTestAndroidDriver<>(new URL("http://"+host+":"+port), dc);
        eyes.setApiKey("9RkMajXrzS1Zu110oTWQps102CHiPRPmeyND99E9iL0G7yAc110");
        eyes.open(driver, "Login Example", "SeeTestAppium");
        eyes.setLogHandler(new StdoutLogHandler(true));
        eyes.setLogHandler(new FileLogger("/Users/justin/repos/applitools/seetest/Appium-Applitools.log", true, true));

    }

    @Test
    public void BadLogin() {
        driver.findElement(By.xpath("//*[@id='usernameTextField']")).sendKeys("myusername");
        driver.findElement(By.xpath("//*[@id='passwordTextField']")).sendKeys("password");
        driver.findElement(By.xpath("//*[@text='Login']")).click();
        new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='Close']")));
        //eyes.setScaleRatio();
        //eyes.getDevicePixelRatio()
        eyes.checkWindow("Login");
        //eyes.checkRegion(By.xpath("//*[@text='Close']"));
        //TestResults results = eyes.close(false);
        eyes.close();
        //assertEquals(true, results.isPassed());
        //driver.findElement(By.xpath("//*[@text='Close']")).click();
    }

    @After
    public void tearDown() {
        driver.quit();
        eyes.abortIfNotClosed();
    }
}