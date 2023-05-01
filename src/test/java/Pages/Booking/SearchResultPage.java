package Pages.Booking;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SearchResultPage extends BasePage {
    private final By resultStats = By.xpath("//*[@data-testid='property-card']");

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getResultStats() {
        List<WebElement> resultStatsElement = driver.findElements(resultStats);
        return resultStatsElement.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<Long> getAllPricesFromStats(List<String> strings) {
        Pattern pattern = Pattern.compile("GEL ([0-9,]+)");
        List<Long> pricesList = new ArrayList<>();
        for (String s : strings) {
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) {
                String priceStr = matcher.group(1);
                Long price = Long.valueOf(priceStr.replaceAll(",", ""));
                pricesList.add(price);
            }
        }
        return pricesList;
    }

    public void setBudgetFilter(String checkBoxNumber, String offsetLeft, String offsetRight) {
        /*Пришлось добавить обходной вариант с try/catch, тк по непонятному алгоритму боковой фильтр
                произвольно менятся от чекбокса на слайдер*/
        try {
            WebElement budgetCheckbox = driver.findElement(By.xpath("//*[contains(text(), 'Your Budget')]//following::div[contains(@data-filters-item, 'pri=" + checkBoxNumber + "')]"));
            budgetCheckbox.click();
        } catch (NoSuchElementException e) {
            try {
                WebElement budgetSliderRight = driver.findElement(By.xpath("//div[@style='left:100%']"));
                Actions sliderAction = new Actions(driver);
                sliderAction.clickAndHold(budgetSliderRight).moveByOffset(findSliderOffset(offsetRight), 0).release().perform();
            } catch (NoSuchElementException e2) {
                e.printStackTrace();
            }
        }

        synchronized (driver) {
            waitOnObject(driver, 5000);
        }
    }

    public WebElement findHighestPriceRatingElement() {
        List<WebElement> propertyCards = driver.findElements(resultStats);

        double maxScore = Double.MIN_VALUE;
        double maxPrice = Double.MIN_VALUE;

        WebElement maxPropertyCard = null;

        for (WebElement propertyCard : propertyCards) {
            double score = Double.parseDouble(propertyCard.findElement(By.xpath("//div[contains(@aria-label, 'Scored')]"))
                    .getText());
            String priceStr = propertyCard.findElement(By.xpath("//*[contains(@data-testid, 'discounted')]"))
                    .getText().replaceAll("[^\\d.]", "");
            double price = Double.parseDouble(priceStr);

            if (score > maxScore || (score == maxScore && price > maxPrice)) {
                maxScore = score;
                maxPrice = price;
                maxPropertyCard = propertyCard;
            }
        }

        return maxPropertyCard;
    }

    public int findSliderOffset(String expression) {
        return switch (expression) {
            case "100" -> 65;
            case "200" -> -100;
            default -> 0;
        };
    }

    public void switchTabAndTakeScreenshot() {
        switchToTab(1);
        takeScreenshot(driver);
    }
}
