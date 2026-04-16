package com.automation.workware.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object representing the Checkout: Your Information page (step one).
 */
public class CheckoutPage extends BasePage {

    private By firstName = By.id("first-name");
    private By lastName = By.id("last-name");
    private By postalCode = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By cancelButton = By.id("cancel");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isOnCheckoutPage() {
        // Swag Labs uses a url fragment for checkout step one
        return wait.waitForUrlContains("checkout-step-one") || wait.waitForTitleContains("Checkout");
    }

    public void enterCheckoutInformation(String fName, String lName, String pCode) {
        wait.waitForElementVisible(firstName).clear();
        wait.waitForElementVisible(firstName).sendKeys(fName);
        wait.waitForElementVisible(lastName).clear();
        wait.waitForElementVisible(lastName).sendKeys(lName);
        wait.waitForElementVisible(postalCode).clear();
        wait.waitForElementVisible(postalCode).sendKeys(pCode);
    }

    /**
     * Clicks Continue and navigates to the overview page.
     */
    public CheckoutOverviewPage clickContinue() {
        wait.waitForElementClickable(continueButton).click();
        return new CheckoutOverviewPage(driver);
    }

    public void clickCancel() {
        wait.waitForElementClickable(cancelButton).click();
    }
}
