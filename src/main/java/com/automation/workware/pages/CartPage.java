package com.automation.workware.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object representing the Cart page.
 */
public class CartPage extends BasePage {

    private By cartItems = By.cssSelector("div.cart_item");
    private By itemName = By.className("inventory_item_name");
    private By removeButton = By.xpath(".//button[text()='Remove']");
    private By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Returns product names present in the cart.
     */
    public List<String> getCartProductNames() {
        List<String> names = new ArrayList<>();
        List<WebElement> items = wait.waitForElementsVisible(cartItems);
        for (WebElement item : items) {
            String name = item.findElement(itemName).getText();
            names.add(name);
        }
        return names;
    }

    /**
     * Removes a product from cart by visible name.
     */
    public void removeProductByName(String productName) {
        By removeForProduct = By.xpath("//div[@class='cart_item' and .//div[text()='" + productName + "']]//button[text()='Remove']");
        wait.waitForElementClickable(removeForProduct).click();
    }

    /**
     * Returns the number of items currently shown in the cart page.
     */
    public int getCartItemsCount() {
        return wait.waitForElementsVisible(cartItems).size();
    }

    /**
     * Clicks the Checkout button and returns a CheckoutPage object.
     */
    public CheckoutPage clickCheckout() {
        wait.waitForElementClickable(checkoutButton).click();
        return new CheckoutPage(driver);
    }

    /**
     * Returns true if the current page appears to be the cart page.
     */
    public boolean isOnCartPage() {
        try {
            // check URL fragment or presence of cart items container
            boolean urlCheck = wait.waitForUrlContains("cart");
            if (urlCheck) return true;
            // fallback: check for cart items container visibility
            return wait.waitForElementsVisible(cartItems).size() >= 0;
        } catch (Exception e) {
            return false;
        }
    }
}