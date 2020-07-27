package com.borzykin.webautomation.tests.base;

import com.borzykin.webautomation.common.provider.DriverFactory;
import com.borzykin.webautomation.modules.CoreModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import io.codearte.jfairy.Fairy;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

/**
 * @author Oleksii B
 */
@TestInstance(PER_CLASS)
@ExtendWith(ExecutionWatcher.class)
public class BaseTest {
    @Inject
    protected Fairy fairy;
    protected static WebDriver driver;

    @BeforeAll
    public void setUp() {
        injectModules();
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
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
