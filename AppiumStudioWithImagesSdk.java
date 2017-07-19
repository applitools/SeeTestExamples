import com.experitest.appium.*;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;
import org.junit.*;
import java.net.URL;
import java.net.MalformedURLException;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import com.applitools.eyes.images.Eyes;
import com.applitools.eyes.RectangleSize;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class SeeTestImagesSdk {
    private String host = "localhost";
    private int port = 8889;
    private String reportDirectory = "reports";
    private String reportFormat = "xml";
    private String testName = "Untitled";
    protected SeeTestAndroidDriver<SeeTestAndroidElement> driver = null;
    protected Eyes eyes = new Eyes();

    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(SeeTestCapabilityType.REPORT_DIRECTORY, reportDirectory);
        dc.setCapability(SeeTestCapabilityType.REPORT_FORMAT, reportFormat);
        dc.setCapability(SeeTestCapabilityType.TEST_NAME, testName);
        dc.setCapability(MobileCapabilityType.UDID, "YOUR DEVICE UDID");
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
        driver = new SeeTestAndroidDriver<>(new URL("http://"+host+":"+port), dc);

        eyes.setHostOS("Android");
        eyes.setApiKey("APPLITOOLS_API_KEY");
        eyes.setHostApp("eriBank");

        Integer width = driver.manage().window().getSize().getWidth();
        Integer height = driver.manage().window().getSize().getHeight();

        eyes.open("Android eriBank", "AppiumStudio + Images SDK", new RectangleSize(width, height));
    }

    @Test
    public void BadLogin() {
        driver.findElement(By.xpath("//*[@id='usernameTextField']")).sendKeys("myusername");
        driver.findElement(By.xpath("//*[@id='passwordTextField']")).sendKeys("password");
        driver.findElement(By.xpath("//*[@text='Login']")).click();
        new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='Close']")));

        File src= driver.getScreenshotAs(OutputType.FILE);

        String fileName = "alert.png";

        try {
            FileUtils.copyFile(src, new File(fileName));
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());

        }

        BufferedImage img;
        try {
            img = ImageIO.read(new File(fileName));
            eyes.checkImage(img, "Bad Login Popup");
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());

        }

        eyes.close();
    }

    @After
    public void tearDown() {
        driver.quit();
        eyes.abortIfNotClosed();
    }
}
