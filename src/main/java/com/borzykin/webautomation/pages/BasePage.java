package com.borzykin.webautomation.pages;

import java.util.List;

import com.borzykin.webautomation.common.utils.DateUtils;
import com.google.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Oleksii B
 */
@Log4j2
public class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    @Inject
    public BasePage(final WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
    }

    protected WebElement getWebElement(final By locator) {
        waitForElementToBeVisible(locator);
        return driver.findElement(locator);
    }

    protected List<WebElement> getWebElements(final By locator) {
        waitForElementToBeVisible(locator);
        return driver.findElements(locator);
    }

    protected void clickElement(final WebElement element) {
        waitForElementToBeClickable(element);
        element.click();
    }

    protected void typeTextToField(final WebElement element, final String text) {
        waitForElementToBeClickable(element);
        element.sendKeys(text);
    }

    protected void typeTextToFieldByChar(final WebElement element, final String text) {
        element.clear();
        for (int i = 0; i < text.length(); i++) {
            final char letter = text.charAt(i);
            element.sendKeys(String.valueOf(letter));
            DateUtils.waitFor(25);
        }
    }

    protected void waitForElementToBeVisible(final By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForElementToBeVisible(final WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementToBeVisible(final WebElement element, final int timeout) {
        final WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementToBeClickable(final By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForElementToBeClickable(final WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitForElementToBeClickable(final WebElement element, final int timeout) {
        final WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void clickJS(final WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
    }

    protected void clickByActions(final WebElement element) {
        final Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.click();
        actions.build().perform();
    }
}
