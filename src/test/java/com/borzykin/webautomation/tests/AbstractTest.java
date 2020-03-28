package com.borzykin.webautomation.tests;

import com.borzykin.webautomation.config.DriverFactory;
import io.codearte.jfairy.Fairy;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

/**
 * @author Oleksii B
 */
public class AbstractTest {
    protected WebDriver driver;
    protected Fairy fairy;

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
        DriverFactory factory = new DriverFactory(browser);
        driver = factory.createDriver();
        driver.manage().window().maximize();
        fairy = Fairy.create();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
