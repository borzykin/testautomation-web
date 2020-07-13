package com.borzykin.webautomation.tests;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.borzykin.webautomation.models.User;
import com.borzykin.webautomation.pages.AbTestPage;
import com.borzykin.webautomation.pages.DropDownPage;
import com.borzykin.webautomation.pages.HomePage;
import com.borzykin.webautomation.rest.RestService;
import com.google.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oleksii B
 */
@Log4j2
@DisplayName("Framework development related tests")
public class FrameworkDevelopmentTests extends BaseTest {
    @Inject
    private HomePage homePage;
    @Inject
    private AbTestPage abTestPage;
    @Inject
    private DropDownPage dropDownPage;
    @Inject
    private ResourceBundle messages;
    @Inject
    private RestService restService;

    @Test
    @Tag("smoke")
    @DisplayName("Logger and AssertJ tests")
    public void testingTests() {
        log.info("T E S T");
        assertThat(2 + 2)
                .as("Sum should be close")
                .isCloseTo(5, Percentage.withPercentage(25));
    }

    @Test
    @Tag("smoke")
    @DisplayName("Page Objects test")
    public void simpleTest() {
        homePage.navigate();
        homePage.clickAbTestLink();
        final String pageTitle = abTestPage.getPageNameText();
        assertThat(pageTitle.matches("(No A/B Test)|(A/B Test Control)|(A/B Test Variation 1)"))
                .as(String.format("Page title '%s' should be 'No A/B Test' or 'A/B Test Control'", pageTitle))
                .isEqualTo(true);
    }

    @Test
    @Tag("smoke")
    @DisplayName("Streams for WebElements test")
    public void simpleCollectionTest() {
        homePage.navigate();
        List<String> filteredLinks = homePage.getAvailableLinks()
                .stream()
                .filter(x -> x.getText().contains("Dynamic"))
                .map(WebElement::getText)
                .collect(Collectors.toList());
        log.info(filteredLinks.toString());
    }

    @Test
    @Tag("regression")
    @DisplayName("jFairy Tests")
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
    @Tag("regression")
    @DisplayName("Rest assured Tests")
    public void restTest() {
        final User user = restService.getUser(1);
        log.info(String.format("Retrieved name: %s", user.getName()));
        log.info(String.format("Retrieved email: %s", user.getEmail()));
        log.info(String.format("Retrieved phone: %s", user.getPhone()));
        log.info(String.format("Retrieved website: %s", user.getWebsite()));
        log.info(String.format("Retrieved address: %s", user.getAddress()));
    }

    @Test
    @Tag("localization")
    @DisplayName("Correct translations tests (resource bundle research)")
    public void localizationTest() {
        homePage.navigate();
        homePage.clickDropDownLink();
        assertThat(dropDownPage.getPageNameText())
                .as("Correct locale should apply")
                .isEqualTo(messages.getString("screen.dropdown.label"));
    }
}
