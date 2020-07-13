package com.borzykin.webautomation.pages;

import com.google.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Oleksii B
 */
@Log4j2
public class FormAuthenticationPage extends BasePage {
    @FindBy(css = "input#username")
    private WebElement usernameInput;
    @FindBy(css = "input#password")
    private WebElement passwordInput;
    @FindBy(css = "button[type=submit]")
    private WebElement submitButton;
    @FindBy(css = "div[data-alert]")
    private WebElement errorAlert;

    @Inject
    public FormAuthenticationPage(final WebDriver driver) {
        super(driver);
    }

    public void enterLoginData(final String username, final String password) {
        log.info("Logging in as {} / {}", username, password);
        typeTextToFieldByChar(usernameInput, username);
        typeTextToFieldByChar(passwordInput, password);
        clickByActions(submitButton);
    }

    public String getErrorMessage() {
        waitForElementToBeVisible(errorAlert, 5_000);
        return errorAlert.getText();
    }
}
