package com.borzykin.webautomation.pages;

import java.util.List;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Oleksii B
 */
public class HomePage extends BasePage {
    @FindBy (css = "a[href^='/']:not([target])")
    private List<WebElement> links;
    @FindBy (linkText = "A/B Testing")
    private WebElement abTestLink;
    @FindBy (linkText = "Dropdown")
    private WebElement dropDown;

    @Inject
    public HomePage(final WebDriver driver) {
        super(driver);
    }

    public void navigate() {
        driver.get("https://the-internet.herokuapp.com/");
    }

    public void clickAbTestLink() {
        clickElement(abTestLink);
    }

    public List<WebElement> getAvailableLinks() {
        return links;
    }

    public void clickDropDownLink() {
        clickElement(dropDown);
    }
}
