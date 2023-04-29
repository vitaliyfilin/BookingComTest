package Pages.Booking;

import Pages.BookingControls.Button;
import Pages.BookingControls.ControlFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;

public class GeniusModalWindow {
    private final WebDriver driver;
    private final ControlFactory controlFactory;

    public GeniusModalWindow(WebDriver driver) {
        this.driver = driver;
        this.controlFactory = new ControlFactory(driver);
    }

    public void waitAndCloseModal() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement textContent = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//*[contains(text(), 'save 10% or more')]")));

        Button closeButton = controlFactory.create(Button.class, By.xpath("//*[contains(@aria-label, 'Dismiss')]"));
        closeButton.click();
    }
}
