package Pages.Booking;

import Pages.BookingControls.Button;
import Pages.BookingControls.ControlFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class GeniusModalWindow extends BasePage {
    private final ControlFactory controlFactory;

    public GeniusModalWindow(WebDriver driver) {
        super(driver);
        this.controlFactory = new ControlFactory(driver);
    }

    public void waitAndCloseModal() {
        try {
            waitForElementToAppear(By.xpath("//*[contains(text(), 'save 10% or more')]"));
            Button closeButton = controlFactory.create(Button.class, By.xpath("//*[contains(@aria-label, 'Dismiss')]"));
            closeButton.click();
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
        }
    }
}
