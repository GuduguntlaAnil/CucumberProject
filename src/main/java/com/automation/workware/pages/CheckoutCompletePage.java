package com.automation.workware.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object representing the Checkout: Complete page (final step).
 */
public class CheckoutCompletePage extends BasePage {

    private By completeHeader = By.className("complete-header");
    private By backHomeButton = By.id("back-to-products");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public boolean isOnCompletePage() {
        try {
            return wait.waitForElementVisible(completeHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCompleteHeaderText() {
        try {
            return wait.waitForElementVisible(completeHeader).getText();
        } catch (Exception e) {
            return null;
        }
    }

    public void clickBackHome() {
        wait.waitForElementClickable(backHomeButton).click();
    }
}
