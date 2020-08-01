package com.borzykin.webautomation.tests.base;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import com.borzykin.webautomation.common.provider.DriverFactory;
import io.qameta.allure.Allure;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

@Log4j2
public class ExecutionWatcher implements TestWatcher {
    @Override
    public void testAborted(ExtensionContext context, Throwable thr) {
        log.info("Test '{}' is aborted", context.getDisplayName());
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> optional) {
        log.info("Test '{}' is disabled", context.getDisplayName());
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        log.info("Test '{}' is passed", context.getDisplayName());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable thr) {
        log.info("Test '{}' is failed", context.getDisplayName());
        // take screenshot and attach to allure
        byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
        try (InputStream is = new ByteArrayInputStream(screenshot)) {
            Allure.addAttachment("Page screenshot", is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
