package com.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
	private WebDriver driver;
	private By productsText=By.xpath("//div[text()='Products']");
	private By filterSection=By.xpath("//select[@class='product_sort_container']/option");
	private By menu=By.xpath("//button[text()='Open Menu']");
	private By logoutButton=By.id("logout_sidebar_link");
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	public String getProductText() {
		return driver.findElement(productsText).getText();
	}
	
	public void clicksOnMenu() {
		driver.findElement(menu).click();
	}
	public boolean isLogoutButtonDisplayed() {
		return driver.findElement(logoutButton).isDisplayed();
	}
	public List<String> getFilterList() {
		List<String> filtersList=new ArrayList<>();
		List<WebElement> filtersText=driver.findElements(filterSection);
		for(WebElement we:filtersText) {
			String text=we.getText();
			filtersList.add(text);
		}
		return filtersList;
	}
	public int getfilterCount() {
		return driver.findElements(filterSection).size();
		
	}
	

}
