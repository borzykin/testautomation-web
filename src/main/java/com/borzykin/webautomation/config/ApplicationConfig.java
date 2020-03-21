package com.borzykin.webautomation.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

/**
 * @author Oleksii B
 */
@Getter
@Log4j
public class ApplicationConfig {
    private String baseUserLogin;
    private String baseUserPassword;
    private int timeout;

    public ApplicationConfig() {
        this.initProperties();
    }

    private void initProperties() {
        try (InputStream fileInput = Files.newInputStream(Paths.get("src/application.properties"))) {
            final Properties properties = new Properties();
            properties.load(fileInput);
            this.baseUserLogin = properties.getProperty("base.user.username");
            this.baseUserPassword = properties.getProperty("base.user.password");
            this.timeout = Integer.parseInt(properties.getProperty("timeout"));
        } catch (IOException e) {
            log.info(String.format("Exception %s", e.getMessage()));
        }

    }
}
