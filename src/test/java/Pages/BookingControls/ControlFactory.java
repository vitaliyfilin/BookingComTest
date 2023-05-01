package Pages.BookingControls;

import Exceptions.ControlInstantiationException;
import Exceptions.ElementNotFoundException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;

public class ControlFactory implements IControlFactory {
    private WebDriver driver;

    public ControlFactory(WebDriver driver) {
        this.driver = driver;
    }

    public <T extends BaseControl> T create(Class<T> controlClass, By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return controlClass.getConstructor(WebElement.class).newInstance(element);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new ControlInstantiationException("Failed to create control instance", e);
        } catch (NoSuchElementException e) {
            throw new ElementNotFoundException("Element not found", e);
        }
    }

}