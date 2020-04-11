package com.borzykin.webautomation.tests;

import com.borzykin.webautomation.modules.CoreModule;
import com.borzykin.webautomation.pages.Page;
import com.google.inject.Guice;
import com.google.inject.Inject;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

/**
 * @author Oleksii B
 */
@TestInstance(PER_CLASS)
public class Tests {
    @Inject
    protected WebDriver driver;
    @Inject
    protected Page page;

    @BeforeAll
    public void setUp() {
        injectModules();
        driver.manage().window().maximize();
    }

    @Test
    public void test() {
        driver.get("https://google.com/");
        page.sysout();
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
        driver.get("http://localhost:8080/");
    }

    @AfterEach
    public void cleanUpSession() {
        driver.manage().deleteAllCookies();
    }
}
