package com.borzykin.webautomation.tests;

import javax.mail.Message;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.borzykin.webautomation.common.utils.EmailUtils;
import com.borzykin.webautomation.models.User;
import com.borzykin.webautomation.pages.AbTestPage;
import com.borzykin.webautomation.pages.DropDownPage;
import com.borzykin.webautomation.pages.FormAuthenticationPage;
import com.borzykin.webautomation.pages.HomePage;
import com.borzykin.webautomation.rest.RestService;
import com.borzykin.webautomation.tests.base.BaseTest;
import com.google.inject.Inject;
import com.opencsv.CSVReader;
import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.assertj.core.api.SoftAssertions;
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
    private FormAuthenticationPage formAuthenticationPage;
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
        final List<String> filteredLinks = homePage.getAvailableLinks()
                .stream()
                .filter(x -> x.getText().contains("Dynamic"))
                .map(WebElement::getText)
                .collect(Collectors.toList());
        log.info(filteredLinks.toString());
    }

    @Test
    @Tag("smoke")
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
    @Tag("smoke")
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
    @Tag("smoke")
    @DisplayName("Input methods tests")
    public void inputTests() {
        homePage.navigate();
        homePage.clickFormAuthenticationLink();
        formAuthenticationPage.enterLoginData("admin", "admin");
        assertThat(formAuthenticationPage.getErrorMessage())
                .as("Error about failed login should be shown")
                .contains(messages.getString("screen.auth.error"));
    }

    @Test
    @Tag("smoke")
    @DisplayName("Correct translations tests (resource bundle research)")
    public void localizationTest() {
        homePage.navigate();
        homePage.clickDropDownLink();
        assertThat(dropDownPage.getPageNameText())
                .as("Correct locale should apply")
                .isEqualTo(messages.getString("screen.dropdown.label"));
    }

    @Test
    @Tag("smoke")
    @DisplayName("Email library implementation tests")
    public void emailTest() {
        // this object should be instantiated inside try-with-resources for real-world usage
        final EmailUtils email = new EmailUtils("qadecf1@gmail.com", "");

        Message[] messagesFrom = email.getMessagesFrom("no-reply@accounts.google.com");
        Message[] messagesWithSubject = email.getMessagesWithSubject("Confirmation instructions for Mailtrap account");
        Message[] messagesWithSubjectFrom = email.getMessagesFromWithSubject("no-reply@accounts.google.com", "Оповещение системы безопасности");
        Message[] messagesTo = email.getMessagesTo("qadecf1+mailfromcode@gmail.com");
        Message[] messagesToWithSubject = email.getMessagesToWithSubject("qadecf1+mailfromcode@gmail.com", "Mail test");
        Message[] messagesToWithSubjectUnread = email.getMessagesToWithSubject("qadecf1+mailfromcode@gmail.com", "Mail test", true);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(messagesFrom).hasSizeGreaterThan(0);
            softAssertions.assertThat(messagesWithSubject).hasSizeGreaterThan(0);
            softAssertions.assertThat(messagesWithSubjectFrom).hasSizeGreaterThan(0);
            softAssertions.assertThat(messagesTo).hasSizeGreaterThan(0);
            softAssertions.assertThat(messagesToWithSubject).hasSizeGreaterThan(0);
            softAssertions.assertThat(messagesToWithSubjectUnread).hasSizeGreaterThan(0);
        });
    }

    @Test
    @Tag("smoke")
    @DisplayName("PDF library implementation test")
    public void pdfTest() {
        try (var document = PDDocument.load(getClass().getClassLoader().getResourceAsStream("sample.pdf"))) {
            final String pdfText = new PDFTextStripper().getText(document);
            log.info("Parsed text size is {} characters:", pdfText.length());
            log.info("Parsed document: \n{}", pdfText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Tag("smoke")
    @DisplayName("CSV library implementation test")
    public void csvTest() {
        // to get country code of Guyana from csv file
        try (var reader = new CSVReader(new FileReader("src/test/resources/country.csv"))) {
            List<String[]> list = reader.readAll();
            for (String[] line : list) {
                if (line[0].equals("Guyana")) {
                    log.info(line[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
