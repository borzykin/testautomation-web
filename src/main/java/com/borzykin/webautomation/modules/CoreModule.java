package com.borzykin.webautomation.modules;

import java.util.Locale;
import java.util.ResourceBundle;

import com.borzykin.webautomation.common.ProjectConfig;
import com.borzykin.webautomation.common.provider.DriverFactory;
import com.borzykin.webautomation.pages.AbTestPage;
import com.borzykin.webautomation.pages.DropDownPage;
import com.borzykin.webautomation.pages.FormAuthenticationPage;
import com.borzykin.webautomation.pages.HomePage;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import io.codearte.jfairy.Fairy;
import org.openqa.selenium.WebDriver;

/**
 * @author Oleksii B
 */
public class CoreModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Fairy.class).toInstance(Fairy.create());
        bind(AbTestPage.class).in(Scopes.SINGLETON);
        bind(HomePage.class).in(Scopes.SINGLETON);
        bind(DropDownPage.class).in(Scopes.SINGLETON);
        bind(FormAuthenticationPage.class).in(Scopes.SINGLETON);
    }

    @Provides @Singleton
    public WebDriver getDriver(final DriverFactory driverFactory) {
        return driverFactory.getDriver();
    }

    @Provides @Singleton
    public ResourceBundle getMessages() {
        Locale.setDefault(new Locale(ProjectConfig.LOCALE));
        return ResourceBundle.getBundle("messages");
    }
}
