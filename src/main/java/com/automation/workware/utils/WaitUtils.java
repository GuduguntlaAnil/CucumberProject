package com.automation.workware.utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * WaitUtils centralizes explicit WebDriver waits used by Page Objects.
 *
 * Purpose:
 * - Provide concise helper methods that encapsulate WebDriverWait + ExpectedConditions.
 * - Keep page object code readable by avoiding repeated WebDriverWait boilerplate.
 *
 * Usage:
 *   WaitUtils wait = new WaitUtils(driver);
 *   WebElement el = wait.waitForElementClickable(locator, 10);
 */
public class WaitUtils {
    private final WebDriver driver;
    private final int DEFAULT_TIMEOUT = 10;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
    }

    /** Wait until the element located by locator is visible, up to timeoutSeconds. */
    public WebElement waitForElementVisible(By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Wait using default timeout. */
    public WebElement waitForElementVisible(By locator) {
        return waitForElementVisible(locator, DEFAULT_TIMEOUT);
    }

    /** Wait until element is clickable and return it. */
    public WebElement waitForElementClickable(By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    /** Wait using default timeout. */
    public WebElement waitForElementClickable(By locator) {
        return waitForElementClickable(locator, DEFAULT_TIMEOUT);
    }

    /** Wait until all elements located by locator are visible. */
    public List<WebElement> waitForElementsVisible(By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /** Wait using default timeout. */
    public List<WebElement> waitForElementsVisible(By locator) {
        return waitForElementsVisible(locator, DEFAULT_TIMEOUT);
    }

    /** Wait until presence of element in DOM (may not be visible). */
    public WebElement waitForPresenceOfElement(By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /** Wait until element located by locator becomes invisible. */
    public boolean waitForInvisibility(By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /** Wait until the page title contains the given fraction. */
    public boolean waitForTitleContains(String titleFraction, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.titleContains(titleFraction));
    }

    /** Wait until the current URL contains the provided fragment. Useful for navigation checks. */
    public boolean waitForUrlContains(String urlFraction, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.urlContains(urlFraction));
    }

    /** Wait until the specified text is present in the element located by locator. */
    public boolean waitForTextToBePresent(By locator, String text, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    // convenience overloads using default timeout
    /** Wait until presence of element in DOM (may not be visible). */
    public WebElement waitForPresenceOfElement(By locator) { return waitForPresenceOfElement(locator, DEFAULT_TIMEOUT); }
    /** Wait until element located by locator becomes invisible. */
    public boolean waitForInvisibility(By locator) { return waitForInvisibility(locator, DEFAULT_TIMEOUT); }
    /** Wait until the page title contains the given fraction. */
    public boolean waitForTitleContains(String titleFraction) { return waitForTitleContains(titleFraction, DEFAULT_TIMEOUT); }
    /** Wait until the current URL contains the provided fragment. Useful for navigation checks. */
    public boolean waitForUrlContains(String urlFraction) { return waitForUrlContains(urlFraction, DEFAULT_TIMEOUT); }
    /** Wait until the specified text is present in the element located by locator. */
    public boolean waitForTextToBePresent(By locator, String text) { return waitForTextToBePresent(locator, text, DEFAULT_TIMEOUT); }
}