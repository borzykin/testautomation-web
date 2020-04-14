package com.borzykin.webautomation.tests;

import com.borzykin.webautomation.models.User;
import com.borzykin.webautomation.pages.AbTestPage;
import com.borzykin.webautomation.pages.HomePage;
import com.borzykin.webautomation.rest.RestService;
import com.google.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oleksii B
 */
@Log4j2
public class FrameworkDevelopmentTests extends BaseTest {
    @Inject
    private HomePage homePage;
    @Inject
    private AbTestPage abTestPage;
    @Inject
    private RestService restService;

    @Test
    public void testingTests() {
        log.info("T E S T");
        assertThat(2 + 2)
                .as("Sum should be close")
                .isCloseTo(5, Percentage.withPercentage(25));
    }

    @Test
    public void simpleTest() {
        homePage.navigate();
        homePage.clickAbTestLink();
        final String pageTitle = abTestPage.getPageNameText();
        assertThat(pageTitle.matches("(No A/B Test)|(A/B Test Control)|(A/B Test Variation 1)"))
                .as(String.format("Page title '%s' should be 'No A/B Test' or 'A/B Test Control'", pageTitle))
                .isEqualTo(true);
    }

    @Test
    public void fakeDataProviderTest() {
        log.info(String.format("Generating name: %s", fairy.person().getFullName()));
        log.info(String.format("Generating email: %s", fairy.person().getEmail()));
        log.info(String.format("Generating password: %s", fairy.person().getPassword()));
        log.info(String.format("Generating company: %s", fairy.company().getName()));
        log.info(String.format("Generating URL: %s", fairy.company().getUrl()));
        assertThat(fairy.person().getFullName())
                .as("Should be new data each time")
                .isNotEqualTo(fairy.person().getFullName());
    }

    @Test
    public void restTest() {
        final User user = restService.getUser(1);
        log.info(String.format("Retrieved name: %s", user.getName()));
        log.info(String.format("Retrieved email: %s", user.getEmail()));
        log.info(String.format("Retrieved phone: %s", user.getPhone()));
        log.info(String.format("Retrieved website: %s", user.getWebsite()));
    }
}
