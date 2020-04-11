package com.borzykin.webautomation.modules;

import com.borzykin.webautomation.config.driver.ChromeDriverManager;
import com.borzykin.webautomation.config.driver.DriverManager;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import org.openqa.selenium.WebDriver;

public class CoreModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DriverManager.class).to(ChromeDriverManager.class).in(Scopes.SINGLETON);
    }

    @Provides
    public WebDriver getDriver(DriverManager driverManager) {
        return driverManager.getDriver();
    }
}
