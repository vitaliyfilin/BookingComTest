package Pages.BookingControls;

import org.openqa.selenium.By;

import java.lang.reflect.InvocationTargetException;

public interface IControlFactory {
    public <T extends BaseControl> T create(Class<T> controlClass, By locator) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
