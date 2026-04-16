package com.automation.workware.pages;

import org.openqa.selenium.WebDriver;

import com.automation.workware.utils.WaitUtils;

/**
 * Base page that provides common utilities (driver + WaitUtils) to all page objects.
 */
public class BasePage {
    protected WebDriver driver;
    protected WaitUtils wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }
}
