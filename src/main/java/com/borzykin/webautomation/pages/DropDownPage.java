package com.borzykin.webautomation.pages;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Oleksii B
 */
public class DropDownPage extends BasePage {
    @FindBy(xpath = "//div[@class='example']/h3")
    private WebElement pageNameLabel;

    @Inject
    public DropDownPage(final WebDriver driver) {
        super(driver);
    }

    public String getPageNameText() {
        return pageNameLabel.getText();
    }
}
