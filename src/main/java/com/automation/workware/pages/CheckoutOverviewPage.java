package com.automation.workware.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object representing the Checkout: Overview page (step two).
 */
public class CheckoutOverviewPage extends BasePage {

    private By overviewItems = By.cssSelector("div.cart_item");
    private By itemName = By.className("inventory_item_name");
    private By finishButton = By.id("finish");
    private By cancelButton = By.id("cancel");
    private By summaryTotal = By.className("summary_total_label");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getOverviewItemNames() {
        List<String> names = new ArrayList<>();
        List<WebElement> items = wait.waitForElementsVisible(overviewItems);
        for (WebElement item : items) {
            String name = item.findElement(itemName).getText();
            names.add(name);
        }
        return names;
    }

    public String getSummaryTotal() {
        return wait.waitForElementVisible(summaryTotal).getText();
    }

    public CheckoutCompletePage clickFinish() {
        wait.waitForElementClickable(finishButton).click();
        return new CheckoutCompletePage(driver);
    }

    public void clickCancel() {
        wait.waitForElementClickable(cancelButton).click();
    }
}
