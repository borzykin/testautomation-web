package com.borzykin.webautomation.config;

import com.google.inject.Inject;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Oleksii B
 */
@Log4j
public class DriverFactory {
    private final String browser;
    private WebDriver driver;

    @Inject
    public DriverFactory() {
        this.browser = System.getProperty("selenium.browser");
    }

    public WebDriver getDriver() {
        if (null == driver) {
            createDriver();
        }
        return driver;
    }

    private void createDriver() {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "headless-chrome":
                WebDriverManager.chromedriver().setup();
                final ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--window-size=1920,1080");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--no-sandbox");
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                log.info(String.format("Do not know how to start: '%s', starting chrome.", browser));
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }
    }
}
