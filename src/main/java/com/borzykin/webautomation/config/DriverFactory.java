package com.borzykin.webautomation.config;

import com.google.inject.Inject;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author Oleksii B
 */
@Log4j
public class DriverFactory {
    private String browser;
    private WebDriver driver;

    @Inject
    public DriverFactory() {
        this.browser = "chrome";
    }

    public WebDriver getDriver() {
        if (null == driver) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        return driver;
    }

}
