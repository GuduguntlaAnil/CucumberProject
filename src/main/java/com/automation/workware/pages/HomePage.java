package com.automation.workware.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.automation.workware.utils.WaitUtils;

/**
 * Page Object representing the application's Home (Products) page.
 * Encapsulates actions and verifications that can be performed on the products/home page.
 *
 * Expected usage:
 *   HomePage home = new HomePage(driver);
 *   String title = home.getProductText();
 */
public class HomePage extends BasePage {
	
	private By productsText=By.xpath("//div[text()='Products']");
	private By filterSection=By.xpath("//select[@class='product_sort_container']/option");
	private By menu=By.xpath("//button[text()='Open Menu']");
	private By logoutButton=By.id("logout_sidebar_link");
	// cart and product related locators
	private By cartIcon = By.className("shopping_cart_link");
	private By cartBadge = By.className("shopping_cart_badge");
	private By productItems = By.cssSelector("div.inventory_item");

	public HomePage(WebDriver driver) {
		super(driver);
	}
	/**
	 * Returns the visible 'Products' text on the page.
	 */
	public String getProductText() {
		return wait.waitForElementVisible(productsText).getText();
	}
	
	/**
	 * Clicks the hamburger/open menu button.
	 */
	public void clicksOnMenu() {
		wait.waitForElementClickable(menu).click();
	}
	/**
	 * Checks whether the logout link is visible in the sidebar.
	 */
	public boolean isLogoutButtonDisplayed() {
		return wait.waitForElementVisible(logoutButton).isDisplayed();
	}
	/**
	 * Clicks logout (opens menu then clicks logout).
	 */
	public void clickLogout() {
		// ensure the menu is open then click logout
		clicksOnMenu();
		wait.waitForElementClickable(logoutButton).click();
	}
	/**
	 * Returns a list of filter option visible texts.
	 */
	public List<String> getFilterList() {
		List<String> filtersList=new ArrayList<>();
		List<WebElement> filtersText=wait.waitForElementsVisible(filterSection);
		for(WebElement we:filtersText) {
			String text=we.getText();
			filtersList.add(text);
		}
		return filtersList;
	}
	/**
	 * Returns the count of filter options available.
	 */
	public int getfilterCount() {
		return wait.waitForElementsVisible(filterSection).size();
		
	}
	
	/**
	 * Adds a product to cart by visible product name.
	 * Locates the inventory_item that contains the given productName and clicks its Add to cart button.
	 */
	public void addProductToCartByName(String productName) {
		// xpath finds the Add to cart button for the matching product name
		By addBtn = By.xpath("//div[@class='inventory_item' and .//div[text()='" + productName + "']]//button[contains(text(),'Add to cart')]");
		wait.waitForElementClickable(addBtn).click();
	}

	/**
	 * Returns the number shown on the cart badge. Returns 0 if badge not present.
	 */
	public int getCartItemCount() {
		try {
			WebElement badge = wait.waitForElementVisible(cartBadge);
			String text = badge.getText();
			return Integer.parseInt(text);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Clicks the cart icon and returns a CartPage object.
	 */
	public CartPage clickOnCart() {
		wait.waitForElementClickable(cartIcon).click();
		return new CartPage(driver);
	}

	/**
	 * Returns list of product names displayed on products page.
	 */
	public List<String> getProductList() {
		List<String> names = new ArrayList<>();
		List<WebElement> items = wait.waitForElementsVisible(productItems);
		for (WebElement item : items) {
			String name = item.findElement(By.className("inventory_item_name")).getText();
			names.add(name);
		}
		return names;
	}

}
