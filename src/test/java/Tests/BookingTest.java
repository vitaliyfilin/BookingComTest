package Tests;

import Helpers.NumberHelper;
import Pages.Booking.BasePage;
import Pages.Booking.GeniusModalWindow;
import Pages.Booking.HomePage;
import Pages.Booking.SearchResultPage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Arrays;

public class BookingTest extends BaseTest {

    @Test
    @Description("Test to open booking.com and search for a location")
    @Severity(SeverityLevel.NORMAL)
    public void searchLocationOnBooking() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
        navigateToBooking();
        setOccupancyOnBooking();
        searchForLocation();
        applyBudgetFilter();
        verifyPricesInBudgetRange();
        clickOnHighestPricePropertyCard();
        takeScreenShotOfCard();
    }

    @Step
    @Description("Navigate to Booking.com")
    private void navigateToBooking() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        driver.get("https://www.booking.com/");
        //Ожидаем появления модального окна
        GeniusModalWindow geniusModalWindow = new GeniusModalWindow(driver);
        geniusModalWindow.waitAndCloseModal();
    }

    @Step
    @Description("Set occupancy")
    private void setOccupancyOnBooking() {
        HomePage homePage = new HomePage(driver);
        homePage.setOccupancy(2, 0, 1);
    }

    @Step
    @Description("Search for location")
    private void searchForLocation() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        HomePage homePage = new HomePage(driver);
        //Дата сдвигается автоматически
        homePage.setCheckInOutDate(Arrays.asList(LocalDate.now(), LocalDate.now().plusDays(4)));
        homePage.searchFor("Kutaisi, Imereti, Georgia");
    }

    @Step
    @Description("Apply budget filter")
    private void applyBudgetFilter() throws InterruptedException {
        SearchResultPage searchResultPage = new SearchResultPage(driver);
        searchResultPage.setBudgetFilter("2", "100", "200");
    }

    @Step
    @Description("Verify prices")
    private void verifyPricesInBudgetRange() {
        SearchResultPage searchResultPage = new SearchResultPage(driver);
        //Проверяем, попадает ли в указанный ценовой диапазон выдача результатов
        Assert.assertTrue(NumberHelper.isWithinRange(searchResultPage.getAllPricesFromStats(searchResultPage.getResultStats()), 5, 500, 4));
    }

    @Step
    @Description("Click on highest price")
    private void clickOnHighestPricePropertyCard() {
        SearchResultPage searchResultPage = new SearchResultPage(driver);
        //Находим элемент, удовлетворяющий всем условиям
        WebElement maxPropertyCard = searchResultPage.findHighestPriceRatingElement();
        maxPropertyCard.click();

        synchronized (driver) {
            BasePage.waitOnObject(driver, 5000);
        }
    }

    @Step
    @Description("Take screenshot")
    private void takeScreenShotOfCard() {
        //Выбираем открывшийся таб с гостиницей
        SearchResultPage searchResultPage = new SearchResultPage(driver);
        searchResultPage.switchTabAndTakeScreenshot();
    }
}


