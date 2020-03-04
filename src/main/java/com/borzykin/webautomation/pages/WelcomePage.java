package com.borzykin.webautomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author Oleksii B
 */
public class WelcomePage extends AbstractPage {
    private static final String URL = "https://the-internet.herokuapp.com/";
    private static final By AB_TESTING_LINK = By.linkText("A/B Testing");

    public WelcomePage(final WebDriver driver) {
        super(driver);
    }

    public void openPage() {
        driver.get(URL);
    }

    public AbTestPage clickAbTestLink() {
        clickElement(AB_TESTING_LINK);
        return new AbTestPage(driver);
    }

    @Override
    public boolean isDisplayed() {
        return false;
    }
}
