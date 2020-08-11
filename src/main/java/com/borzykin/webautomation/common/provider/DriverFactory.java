package com.borzykin.webautomation.common.provider;

import java.net.MalformedURLException;
import java.net.URL;

import com.borzykin.webautomation.common.ProjectConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * @author Oleksii B
 */
@Log4j2
public final class DriverFactory {
    private static WebDriver driver;

    private DriverFactory() {
        throw new IllegalArgumentException("Suppress default constructor");
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            createDriver();
        }
        return driver;
    }

    private static void createDriver() {
        switch (ProjectConfig.getBrowser()) {
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
            case "remote":
                final DesiredCapabilities capability = new DesiredCapabilities();
                capability.setBrowserName("chrome");
                capability.setVersion("84");
                try {
                    driver = new RemoteWebDriver(new URL(ProjectConfig.REMOTE_BASE_URL), capability);
                } catch (MalformedURLException e) {
                    log.error(e.getMessage());
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
