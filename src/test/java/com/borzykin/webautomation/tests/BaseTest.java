package com.borzykin.webautomation.tests;

import com.borzykin.webautomation.config.DriverFactory;
import io.codearte.jfairy.Fairy;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

/**
 * @author Oleksii B
 */
@TestInstance(PER_CLASS)
public class BaseTest {
    protected WebDriver driver;
    protected Fairy fairy;

    @BeforeAll
    public void setUp() {
        DriverFactory factory = new DriverFactory("chrome");
        driver = factory.createDriver();
        driver.manage().window().maximize();
        fairy = Fairy.create();
    }

    @AfterAll
    public void closeBrowser() {
        driver.quit();
    }

    @BeforeEach
    public void setMixpanelAutomationCookies() {
        driver.get("http://localhost:8080/");
    }

    @AfterEach
    public void cleanUpSession() {
        driver.manage().deleteAllCookies();
    }
}
