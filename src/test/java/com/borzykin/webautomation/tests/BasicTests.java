package com.borzykin.webautomation.tests;

import com.borzykin.webautomation.pages.AbTestPage;
import com.borzykin.webautomation.pages.WelcomePage;
import lombok.extern.log4j.Log4j;
import org.assertj.core.data.Percentage;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Oleksii B
 */
@Log4j
public class BasicTests extends AbstractTest {
    @Test
    public void testingTests() {
        log.info("T E S T");
        assertThat(2 + 2)
                .as("Sum should be close")
                .isCloseTo(5, Percentage.withPercentage(25));
    }

    @Test
    public void simpleTest() {
        WelcomePage welcomePage = new WelcomePage(driver);
        welcomePage.openPage();
        AbTestPage abTestPage = welcomePage.clickAbTestLink();
        String pageTitle = abTestPage.getPageNameText();
        assertThat(pageTitle.matches("(No A/B Test)|(A/B Test Control)"))
                .as("Page title [" + pageTitle + "] should be 'No A/B Test' or 'A/B Test Control'")
                .isEqualTo(true);
    }
}
