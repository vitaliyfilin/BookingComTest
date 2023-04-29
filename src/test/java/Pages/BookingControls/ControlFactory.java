package Pages.BookingControls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;

public class ControlFactory implements IControlFactory {
    private WebDriver driver;

    public ControlFactory(WebDriver driver) {
        this.driver = driver;
    }

    public <T extends BaseControl> T create(Class<T> controlClass, By locator) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        WebElement element = driver.findElement(locator);
        return controlClass.getConstructor(WebElement.class).newInstance(element);
    }
}