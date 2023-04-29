package Pages.BookingControls;

import org.openqa.selenium.WebElement;

public class BaseControl {
    protected WebElement element;

    public BaseControl(WebElement element) {
        this.element = element;
    }

    public void click() {
        element.click();
    }

    public String getText() {
        return element.getText();
    }

    public void sendKeys(String text) {
        element.sendKeys(text);
    }
}