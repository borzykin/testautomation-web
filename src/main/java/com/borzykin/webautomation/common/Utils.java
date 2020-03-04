package com.borzykin.webautomation.common;

import lombok.extern.log4j.Log4j;

/**
 * @author Oleksii B
 */
@Log4j
public final class Utils {
    private Utils() {
        throw new UnsupportedOperationException("Suppress default constructor");
    }

    public static void sleep(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.info(String.format("Exception %s", e.getMessage()));
        }
    }
}
