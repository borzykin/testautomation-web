package com.borzykin.webautomation.tests;

import lombok.extern.log4j.Log4j;
import org.assertj.core.data.Percentage;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

@Log4j
public class BasicTests {
    @Test
    public void testingTests() {
        log.info("T E S T");
        assertThat(2 + 2)
                .as("Sum should be close")
                .isCloseTo(5, Percentage.withPercentage(25));
    }
}
