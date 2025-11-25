package stepDefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Hooks {

    public static WebDriver driver;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown(io.cucumber.java.Scenario scenario) {

        // Create screenshots folder if not exists
        File screenshotFolder = new File("screenshots");
        if (!screenshotFolder.exists()) {
            screenshotFolder.mkdir();
        }

        // Take screenshot ONLY if scenario passed
        if (!scenario.isFailed()) {
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File destination = new File("screenshots/" + scenario.getName() + ".png");
                Files.copy(screenshot.toPath(), destination.toPath());
                System.out.println("Screenshot saved: " + destination.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        driver.quit();
    }
}



