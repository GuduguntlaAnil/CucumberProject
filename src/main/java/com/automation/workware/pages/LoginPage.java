package com.automation.workware.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object representing the application Login page.
 * Encapsulates locators and actions that can be performed on the login page.
 * Use this class from step definitions or tests to interact with the login page.
 */
public class LoginPage extends BasePage {
	
	private By emailId=By.id("user-name");
	private By password=By.id("password");
	private By submitButton=By.id("login-button");
	private By acceptedUserNamesText=By.xpath("//div[@id='login_credentials']/h4");
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	/**
	 * Returns the page title for basic verification.
	 */
	public String getLoginPageTitle() {
		return driver.getTitle();
		
	}
	
	/**
	 * Returns true if the accepted usernames instruction text is visible on the page.
	 */
	public boolean isacceptedUserNamesTextExists() {
		return wait.waitForElementVisible(acceptedUserNamesText).isDisplayed();
		
	}
   /**
    * Enter the username into the username input.
    */
   public void enterUserName(String userName) {
	   wait.waitForElementVisible(emailId).sendKeys(userName);
   }
   /**
    * Enter the password into the password input.
    */
   public void enterPassword(String pwd) {
	   wait.waitForElementVisible(password).sendKeys(pwd);
   }
   /**
    * Clicks the login button. Use after entering credentials.
    */
   public void clickOnLogin() {
	   wait.waitForElementClickable(submitButton).click();
   }
   /**
    * Returns the current URL of the page (useful to assert navigation).
    */
   public String getSwaglabsUrl(){ 
	   return driver.getCurrentUrl();
	   }
   
   /**
    * Performs a login and returns a HomePage object on success.
    */
   public HomePage doLogin(String userName,String pwd) {
	   wait.waitForElementVisible(emailId).sendKeys(userName);
	   wait.waitForElementVisible(password).sendKeys(pwd);
	   wait.waitForElementClickable(submitButton).click();
	   
	   return new HomePage(driver);
	}
}