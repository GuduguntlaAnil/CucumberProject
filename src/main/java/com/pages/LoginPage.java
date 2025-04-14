package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
	private WebDriver driver;
	
	private By emailId=By.id("user-name");
	private By password=By.id("password");
	private By submitButton=By.id("login-button");
	private By acceptedUserNamesText=By.xpath("//div[@id='login_credentials']/h4");
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	public String getLoginPageTitle() {
		return driver.getTitle();
		
	}
	
	public boolean isacceptedUserNamesTextExists() {
		return driver.findElement(acceptedUserNamesText).isDisplayed();
		
	}
   public void enterUserName(String userName) {
	   driver.findElement(emailId).sendKeys(userName);
   }
   public void enterPassword(String pwd) {
	   driver.findElement(password).sendKeys(pwd);
   }
   public void clickOnLogin() {
	   driver.findElement(submitButton).click();
   }
   public String getSwaglabsUrl(){ 
	   return driver.getCurrentUrl();
	   }
   
   public HomePage doLogin(String userName,String pwd) {
	   driver.findElement(emailId).sendKeys(userName);
	   driver.findElement(password).sendKeys(pwd);
	   driver.findElement(submitButton).click();
	   
	   return new HomePage(driver);
	}
}
   
