package com.borzykin.webautomation.pages;

import java.util.List;

import com.borzykin.webautomation.config.ApplicationConfig;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Oleksii B
 */
@Log4j2
public abstract class AbstractPage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public AbstractPage(final WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, new ApplicationConfig().getTimeout());
        PageFactory.initElements(driver, this);
    }

    public abstract void navigate();

    protected WebElement getWebElement(final By locator) {
        waitForElementToBeVisible(locator);
        return driver.findElement(locator);
    }

    protected List<WebElement> getWebElements(final By locator) {
        waitForElementToBeVisible(locator);
        return driver.findElements(locator);
    }

    protected void clickElement(final By locator) {
        waitForElementToBeClickable(locator);
        getWebElement(locator).click();
    }

    protected void clickElement(final WebElement element) {
        waitForElementToBeClickable(element);
        element.click();
    }

    protected void typeTextToField(final WebElement element, final String text) {
        waitForElementToBeClickable(element);
        element.sendKeys(text);
    }

    protected void waitForElementToBeVisible(final By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForElementToBeClickable(final By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForElementToBeClickable(final WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}
