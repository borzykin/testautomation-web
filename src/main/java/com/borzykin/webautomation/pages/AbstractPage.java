package com.borzykin.webautomation.pages;

import java.util.List;

import com.borzykin.webautomation.config.ApplicationConfig;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Oleksii B
 */
@Log4j
public abstract class AbstractPage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public AbstractPage(final WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, new ApplicationConfig().getTimeout());
    }

    public abstract boolean isDisplayed();

    protected WebElement getWebElement(final By locator) {
        waitForVisibilityOfElement(locator);
        return driver.findElement(locator);
    }

    protected List<WebElement> getWebElements(final By locator) {
        waitForVisibilityOfElement(locator);
        return driver.findElements(locator);
    }

    protected void clickElement(final By locator) {
        waitForElementToBeClickable(locator);
        getWebElement(locator).click();
    }

    protected void typeTextToField(final By locator, final String text) {
        waitForVisibilityOfElement(locator);
        getWebElement(locator).sendKeys(text);
    }

    protected void waitForVisibilityOfElement(final By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForElementToBeClickable(final By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
