package Infrastructure;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class Screenshot {
    public static void takeScreenshot(WebDriver driver) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;

            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);

            FileUtils.copyFile(srcFile, new File("screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
