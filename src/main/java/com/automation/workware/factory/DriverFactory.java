package com.automation.workware.factory;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * DriverFactory creates and manages WebDriver instances.
 *
 * Design notes:
 * - Uses a ThreadLocal<WebDriver> so each test thread gets its own WebDriver instance
 *   which is important for parallel execution.
 * - The init_driver method configures drivers via WebDriverManager and returns the
 *   driver for the current thread via getDriver().
 *
 * Supported browser values (case-insensitive):
 *   chrome, chrome-headless, firefox, firefox-headless, edge, safari, remote
 *
 * For remote (Grid) execution, set system properties:
 *   -DremoteUrl=http://<host>:4444/wd/hub
 *   -DremoteBrowser=chrome
 */
public class DriverFactory {
    public WebDriver driver;
    // ThreadLocal ensures thread-safety when running tests in parallel
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    /**
     * Initialize a browser driver and register it in the ThreadLocal storage.
     * Supported values (case-insensitive): "chrome", "chrome-headless", "firefox",
     * "firefox-headless", "edge", "safari", "remote".
     * This method also maximizes the window and clears cookies for a clean session.
     */
    public WebDriver init_driver(String browser_prop) {
        String browser_maven = System.getProperty("browser");
        String browser = browser_maven != null ? browser_maven : browser_prop;
        if (browser == null) {
            browser = "chrome"; // default
        }
        browser = browser.toLowerCase().trim();

        System.out.println("browser is " + browser);

        try {
            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    tlDriver.set(new ChromeDriver());
                    break;

                case "chrome-headless":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--window-size=1920,1080");
                    tlDriver.set(new ChromeDriver(chromeOptions));
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    tlDriver.set(new FirefoxDriver());
                    break;

                case "firefox-headless":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    // setHeadless(boolean) may not exist in older/newer Selenium libs; use argument for compatibility
                    firefoxOptions.addArguments("-headless");
                    tlDriver.set(new FirefoxDriver(firefoxOptions));
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    tlDriver.set(new EdgeDriver());
                    break;

                case "safari":
                    // SafariDriver comes with Safari on macOS; no WebDriverManager support
                    tlDriver.set(new SafariDriver());
                    break;

                case "remote":
                    // Remote (Selenium Grid / Selenium Server)
                    String remoteUrl = System.getProperty("remoteUrl", "http://localhost:4444/wd/hub");
                    String remoteBrowser = System.getProperty("remoteBrowser", "chrome");
                    DesiredCapabilities caps = new DesiredCapabilities();
                    caps.setBrowserName(remoteBrowser);
                    try {
                        tlDriver.set(new RemoteWebDriver(new URL(remoteUrl), caps));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException("Invalid remoteUrl: " + remoteUrl, e);
                    }
                    break;

                default:
                    System.out.println("please pass the correct browser value " + browser);
                    break;
            }

            // perform basic session setup for every driver if it was created
            if (getDriver() != null) {
                getDriver().manage().deleteAllCookies();
                try {
                    getDriver().manage().window().maximize();
                } catch (Exception e) {
                    // Some drivers (remote headless, etc.) may not support maximize; ignore
                    System.out.println("could not maximize window: " + e.getMessage());
                }
            }

            return getDriver();
        } catch (Exception e) {
            // ensure any partially created driver is cleaned up
            if (getDriver() != null) {
                try {
                    getDriver().quit();
                } catch (Exception ex) {
                    // ignore
                }
                tlDriver.remove();
            }
            throw e;
        }
    }

    // gives each thread its own copy of webdriver (no clashes in parallel)
    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    // helper to quit and remove driver for current thread
    public static void quitDriver() {
        WebDriver driver = tlDriver.get();
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                // ignore
            }
            tlDriver.remove();
        }
    }
}