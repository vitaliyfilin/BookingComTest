package Pages.Booking;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

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

    public List<Long> getAllPricesFromStats(List<String> strings, String currencyAbbreviation) {
        Pattern pattern = Pattern.compile(String.format("%s ([0-9,]+)", currencyAbbreviation.toUpperCase()));
        return strings.stream()
                .map(pattern::matcher)
                .filter(Matcher::find)
                .map(matcher -> matcher.group(1))
                .map(priceStr -> priceStr.replaceAll(",", ""))
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }

    public void setBudgetFilter(String checkBoxNumber, String offsetLeft, String offsetRight) {
        /*Пришлось добавить обходной вариант с try/catch, тк по непонятному алгоритму боковой фильтр
                произвольно менятся от чекбокса на слайдер*/
        try {
            WebElement budgetCheckbox = driver.findElement(By.xpath("//*[contains(text(), 'Your Budget')]//following::div[contains(@data-filters-item, 'pri=" + checkBoxNumber + "')]"));
            budgetCheckbox.click();

            synchronized (driver) {
                waitOnObject(driver, 3000);
            }

        } catch (NoSuchElementException e) {
            try {
                WebElement budgetSliderRight = driver.findElement(By.xpath("//div[@style='left:100%']"));
                Actions sliderAction = new Actions(driver);
                sliderAction.clickAndHold(budgetSliderRight).moveByOffset(findSliderOffset(offsetRight), 0).release().perform();
            } catch (NoSuchElementException e2) {
                e.printStackTrace();
            }

            synchronized (driver) {
                waitOnObject(driver, 3000);
            }

        }
    }

    public WebElement findHighestPriceRatingElement() {
        List<WebElement> propertyCards = driver.findElements(resultStats);

        double maxScore = Double.MIN_VALUE;
        double maxPrice = Double.MIN_VALUE;

        WebElement maxPropertyCard = null;

        for (WebElement propertyCard : propertyCards) {
            double score = 0.0;
            double price = 0.0;
            try {
                score = Double.parseDouble(propertyCard.findElement(By.xpath(".//div[contains(@aria-label, 'Scored')]"))
                        .getText());
                String priceStr = propertyCard.findElement(By.xpath(".//*[contains(@data-testid, 'discounted')]"))
                        .getText().replaceAll("[^\\d.]", "");
                price = Double.parseDouble(priceStr);
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                continue;
            }

            if (score >= maxScore && price >= maxPrice) {
                maxScore = score;
                maxPrice = price;
                maxPropertyCard = propertyCard;
            }
        }
        assert maxPropertyCard != null;
        return maxPropertyCard.findElement(By.xpath(".//*[contains(@data-testid, 'title')]"));
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
