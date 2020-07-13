package com.borzykin.webautomation.common.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import lombok.extern.log4j.Log4j2;

/**
 * @author Oleksii B
 */
@Log4j2
public final class DateUtils {
    private DateUtils() {
        throw new AssertionError("Suppress default constructor");
    }

    public static ZonedDateTime parseToZonedDateTime(final String date, final String pattern) {
        return ZonedDateTime.of(
                LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern)),
                LocalTime.MAX, ZoneId.systemDefault());
    }

    void waitFor(final long mills) {
        try {
            TimeUnit.MILLISECONDS.sleep(mills);
        } catch (InterruptedException ex) {
            log.error(ex.getMessage());
        }
    }
}
