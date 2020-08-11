package com.borzykin.webautomation.common;

/**
 * @author Oleksii B
 */
public final class ProjectConfig {
    public static final String CLIENT_BASE_URL = System.getProperty("client.baseUrl", "https://the-internet.herokuapp.com/");
    public static final String REMOTE_BASE_URL = System.getProperty("remote.baseUrl", "http://54.216.123.42:4444/wd/hub");
    public static final String LOCALE = System.getProperty("locale", "en");

    private ProjectConfig() {
        throw new AssertionError("Suppress default constructor");
    }

    public static String getBrowser() {
        return System.getProperty("browser", "chrome").toLowerCase();
    }
}
