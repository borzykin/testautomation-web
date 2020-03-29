package com.borzykin.webautomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Oleksii B
 */
public class AbTestPage extends AbstractPage {
    @FindBy (xpath = "//div[@class='example']/h3")
    private WebElement pageNameLabel;

    public AbTestPage(final WebDriver driver) {
        super(driver);
    }

    @Override
    public void navigate() {
        driver.get("https://the-internet.herokuapp.com/abtest");
    }

    public String getPageNameText() {
        return pageNameLabel.getText();
    }

}
