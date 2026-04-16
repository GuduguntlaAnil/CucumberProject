package com.automation.workware.hooks;

import java.util.Properties;

import org.junit.Assume;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.automation.workware.factory.DriverFactory;
import com.automation.workware.utils.ConfigReader;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/**
 * ApplicationHooks contains Cucumber lifecycle hooks that run before and after scenarios.
 *
 * Responsibilities:
 * - Load configuration properties before the test suite runs.
 * - Launch and quit the browser for each scenario.
 * - Skip scenarios annotated with @Skip.
 * - Capture a screenshot and attach to the scenario if it fails.
 *
 * Notes on ordering:
 * - The @Before methods use 'order' to control execution order. getProperty() runs
 *   before launchBrowser() so the browser selection can be read from configuration.
 * - The @After methods run in reverse order; quitBrowser() will run before tearDown()
 *   based on the order values used here.
 */
public class ApplicationHooks {
  private DriverFactory driverFactory;
  private WebDriver driver;
  private ConfigReader configReader;
  Properties prop;
  
  /**
   * Skip any scenario annotated with @Skip. Uses JUnit's Assume to mark the scenario
   * as skipped so it does not continue execution.
   * order=0 ensures this runs early so expensive setup is avoided for skipped scenarios.
   */
  @Before(value="@Skip",order=0)
  public void skip_scenario(Scenario scenario){
	  System.out.println("Skipped scenario is"+scenario.getName());
	  Assume.assumeTrue(false);

  }
  
  /**
   * Load configuration properties before other setup steps.
   * This populates the 'prop' field with values like 'browser' and 'base.url'.
   */
  @Before(order=1)
  public void getProperty() {
	  configReader=new ConfigReader();
	 prop= configReader.init_prop();
  }
  
  /**
   * Initialize the browser using DriverFactory. The browser name is read from
   * configuration (prop.getProperty("browser")).
   * This runs after properties are loaded (order=2).
   */
  @Before(order=2)
  public void launchBrowser() {
	 String browserName= prop.getProperty("browser");
	 driverFactory=new DriverFactory();
	 driver=driverFactory.init_driver(browserName);
	  
  }
  
  /**
   * Quit the browser after the scenario completes. Marked with order=0 so it runs
   * before additional teardown actions that may depend on the scenario state.
   */
  @After(order=0)
  public void quitBrowser(){
	 driver.quit();
  }
  
  /**
   * If a scenario failed, capture a screenshot and attach it to the Cucumber report.
   * This helps debugging by providing the browser state at failure time.
   */
  @After(order=1)
  public void tearDown(Scenario scenario) throws InterruptedException{
	  if(scenario.isFailed()) {
		  String screenshotName=scenario.getName().replace(" ","_");
		byte[] sourcePath=  ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		scenario.attach(sourcePath, "image/png", screenshotName);
		
	  }
  }
  
  
  
    

}