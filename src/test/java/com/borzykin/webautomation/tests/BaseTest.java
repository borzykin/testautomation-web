package com.borzykin.webautomation.tests;

import com.borzykin.webautomation.modules.CoreModule;
import com.borzykin.webautomation.pages.BasePage;
import com.google.inject.Guice;
import com.google.inject.Inject;
import io.codearte.jfairy.Fairy;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

/**
 * @author Oleksii B
 */
@TestInstance(PER_CLASS)
public class BaseTest {
    @Inject
    protected WebDriver driver;

    protected Fairy fairy;

    @BeforeAll
    public void setUp() {
        injectModules();
        driver.manage().window().maximize();
        fairy = Fairy.create();
    }

    private void injectModules() {
        Guice.createInjector(
                new CoreModule()
        ).injectMembers(this);
    }

    @AfterAll
    public void closeBrowser() {
        driver.quit();
    }

    @BeforeEach
    public void navigateToURL() {
        driver.get("https://the-internet.herokuapp.com/redirector");
    }

    @AfterEach
    public void cleanUpSession() {
        driver.manage().deleteAllCookies();
    }
}
