package com.borzykin.webautomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Oleksii B
 */
public class WelcomePage extends AbstractPage {
    @FindBy (linkText = "A/B Testing")
    private WebElement abTestLink;

    public WelcomePage(final WebDriver driver) {
        super(driver);
    }

    @Override
    public void navigate() {
        driver.get("https://the-internet.herokuapp.com/");
    }

    public AbTestPage clickAbTestLink() {
        clickElement(abTestLink);
        return new AbTestPage(driver);
    }


}
