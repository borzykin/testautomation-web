package com.borzykin.webautomation.config;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import lombok.Cleanup;
import lombok.Getter;
import lombok.SneakyThrows;

/**
 * @author Oleksii B
 */
@Getter
public class ApplicationConfig {
    private String baseUserLogin;
    private String baseUserPassword;
    private int timeout;

    public ApplicationConfig() {
        this.initProperties();
    }

    @SneakyThrows
    private void initProperties() {
        @Cleanup final InputStream fileInput = Files.newInputStream(Paths.get("src/application.properties"));
        final Properties properties = new Properties();
        properties.load(fileInput);
        this.baseUserLogin = properties.getProperty("base.user.username");
        this.baseUserPassword = properties.getProperty("base.user.password");
        this.timeout = Integer.parseInt(properties.getProperty("timeout"));
    }
}
