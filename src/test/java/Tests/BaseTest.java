package Tests;

import Infrastructure.AllureCsvReportGenerator;
import Infrastructure.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    public static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        //Тест проходит на грузинской локале, тк букинг не работает с российской
        String locale = "ka-GE";
        driver = DriverFactory.createDriver("chrome", locale);
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        //Генерация отчета по последнему json от Allure в виде csv
        AllureCsvReportGenerator.GenerateCsvReport();
    }
}
