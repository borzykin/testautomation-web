package com.borzykin.webautomation.modules;

import com.borzykin.webautomation.config.DriverFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.openqa.selenium.WebDriver;

/**
 * @author Oleksii B
 */
public class CoreModule extends AbstractModule {
    @Override
    protected void configure() {
        // future binding will be here
    }

    @Provides @Singleton
    public WebDriver getDriver(final DriverFactory driverFactory) {
        return driverFactory.getDriver();
    }
}
