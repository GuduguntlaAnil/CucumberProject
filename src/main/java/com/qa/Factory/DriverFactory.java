package com.qa.Factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;


public class DriverFactory {
 public WebDriver driver;
 public static ThreadLocal<WebDriver> tlDriver=new ThreadLocal<>();
 public WebDriver init_driver(String browser) {
	 System.out.println("browser is "+browser);
	 if(browser.equals("chrome")) {
		 WebDriverManager.chromedriver().setup();
		 tlDriver.set(new ChromeDriver());
		 
	 }
	 else if(browser.equals("firefox")) {
		 WebDriverManager.firefoxdriver().setup();
		 tlDriver.set(new FirefoxDriver());
		 
	 }
	 else if(browser.equals("safari")) {
		 
		 tlDriver.set(new SafariDriver());
		 
	 }
	 else {
		 System.out.println("please pass the correct browser value "+browser);
	 }
	 getDriver().manage().deleteAllCookies();
	 getDriver().manage().window().maximize();
	 return getDriver();
	 
 }
 public static synchronized WebDriver getDriver() {
	 
	return tlDriver.get();
	 
 }
}
