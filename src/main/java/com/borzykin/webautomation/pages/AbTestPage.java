package com.borzykin.webautomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author Oleksii B
 */
public class AbTestPage extends AbstractPage {
    private static final By PAGE_NAME_TEXT = By.xpath("//div[@class='example']/h3");

    public AbTestPage(final WebDriver driver) {
        super(driver);
    }

    public String getPageNameText() {
        return getWebElement(PAGE_NAME_TEXT).getText();
    }

    @Override
    public boolean isDisplayed() {
        return false;
    }
}
