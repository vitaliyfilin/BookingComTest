package Pages.Booking;

import Pages.BookingControls.Button;
import Pages.BookingControls.ControlFactory;
import Pages.BookingControls.Field;
import Pages.BookingControls.SearchBox;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;

public class HomePage extends BasePage {
    private final By searchBox = By.xpath("//input[@name='ss']");
    private final By searchButton = By.xpath("//*[text()='Search']");
    private final By checkInField = By.xpath("//*[@data-testid='date-display-field-start']");
    private final ControlFactory controlFactory;

    public HomePage(WebDriver driver) {
        super(driver);
        this.controlFactory = new ControlFactory(driver);
    }

    public void searchFor(String searchQuery) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        SearchBox searchBoxElement = controlFactory.create(SearchBox.class, searchBox);
        searchBoxElement.sendKeys(searchQuery);
        Button searchButtonElement = controlFactory.create(Button.class, searchButton);
        searchButtonElement.click();
    }

    public void setCheckInOutDate(List<LocalDate> localDate) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Field field = controlFactory.create(Field.class, checkInField);
        field.click();

        for (LocalDate i : localDate) {
            WebElement checkInOutDate = driver.findElement(By.xpath("//*[@data-date='" + i.toString() + "']"));
            checkInOutDate.click();
        }
    }

    public void setOccupancy(int setAdults, int setChildren, int setRooms) {
        WebElement openElements = driver.findElement(By.xpath("//*[@data-testid='occupancy-config']"));
        openElements.click();

        setQuantity(driver.findElement(By.xpath("//*[@id='group_adults']")), setAdults);
        setQuantity(driver.findElement(By.xpath("//*[@id='group_children']")), setChildren);
        setQuantity(driver.findElement(By.xpath("//*[@id='no_rooms']")), setRooms);
    }

    public void setQuantity(WebElement element, int targetQuantity) {
        int currentQuantity = getQuantity(element);
        if (currentQuantity != targetQuantity) {
            WebElement button = currentQuantity > targetQuantity ?
                    element.findElement(By.xpath(".//following::button[1]")) :
                    element.findElement(By.xpath(".//following::button[2]"));
            int diff = Math.abs(currentQuantity - targetQuantity);
            for (int i = 0; i < diff; i++) {
                button.click();
            }
        }
    }

    public int getQuantity(WebElement webElement) {
        return Integer.parseInt(webElement.getAttribute("aria-valuenow"));
    }

}
