package com.borzykin.webautomation.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Oleksii B
 */
@Log4j
public class DriverFactory {
    private final String browser;
    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public DriverFactory(final String browser) {
        this.browser = browser.toLowerCase().trim();
    }

    public WebDriver createDriver() {
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver());
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver());
                break;
            default:
                log.info(String.format("Do not know how to start: '%s', starting chrome.", browser));
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver());
                break;
        }
        return driver.get();
    }
}
