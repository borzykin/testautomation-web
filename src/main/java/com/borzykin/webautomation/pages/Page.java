package com.borzykin.webautomation.pages;

import com.google.inject.Inject;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Oleksii B
 */
@Log4j
public class Page {
    protected WebDriver driver;

    @Inject
    public Page(final WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    protected void clickElement(final WebElement element) {
        element.click();
    }

    public void sysout() {
        System.out.println("Hello");
    }
}
