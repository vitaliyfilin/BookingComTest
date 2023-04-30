package Pages.Booking;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

public class BasePage {

    private static final Duration TIMEOUT = Duration.ofSeconds(5); //seconds
    private static final Duration POLLING = Duration.ofMillis(100); //milliseconds

    protected WebDriver driver;
    private final WebDriverWait wait;

    private ArrayList<String> tabHandles;


    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEOUT, POLLING);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, (int) TIMEOUT.getSeconds()), this);
        this.tabHandles = new ArrayList<>(driver.getWindowHandles());
    }

    protected void waitForElementToAppear(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForElementToDisappear(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void waitForTextToDisappear(By locator, String text) {
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, text)));
    }

    public static synchronized void waitOnObject(Object obj, long timeoutMillis) {
        try {
            obj.wait(timeoutMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void switchToTab(int tabIndex) {
        driver.switchTo().window(tabHandles.get(tabIndex));
    }

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