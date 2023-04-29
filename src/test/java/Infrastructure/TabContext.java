package Infrastructure;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

public class TabContext {
    private WebDriver driver;
    private ArrayList<String> tabHandles;

    public TabContext(WebDriver driver) {
        this.driver = driver;
        this.tabHandles = new ArrayList<>(driver.getWindowHandles());
    }

    public void switchToTab(int tabIndex) {
        driver.switchTo().window(tabHandles.get(tabIndex));
    }
}