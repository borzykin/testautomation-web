package com.borzykin.webautomation.tests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.borzykin.webautomation.common.provider.DriverFactory;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ExecutionWatcher implements TestWatcher {
    @Override
    public void testFailed(ExtensionContext exCont, Throwable thr) {
        // take screenshot and attach to allure
        byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
        try (InputStream is = new ByteArrayInputStream(screenshot)) {
            Allure.addAttachment("Page screenshot", is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
