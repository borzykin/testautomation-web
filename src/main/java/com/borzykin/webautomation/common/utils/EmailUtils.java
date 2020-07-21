package com.borzykin.webautomation.common.utils;

import java.io.IOException;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.AndTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.RecipientStringTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import com.borzykin.webautomation.common.utils.model.EmailAddress;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPSSLStore;
import lombok.extern.log4j.Log4j2;

/**
 * @author Oleksii B
 */
@Log4j2
public class EmailUtils implements AutoCloseable {
    protected Session session;
    protected Store store;
    protected IMAPFolder folder;
    protected String username;
    protected String password;
    private boolean connected;
    private static final String URL = "imap.gmail.com";
    private static final int PORT = 993;
    private static final String PROTOCOL = "imaps";

    public EmailUtils(final String username, final String password) {
        this.username = username;
        this.password = password;
        this.connected = false;

        final Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", 465);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        props.setProperty("mail.store.protocol", PROTOCOL);
        props.setProperty("mail.user", username);
        props.setProperty("mail.password", password);

        session = Session.getInstance(props);

        final URLName urlName = new URLName(PROTOCOL, URL, PORT, null, username, password);
        store = new IMAPSSLStore(session, urlName);
    }

    private void connect() {
        if (!connected) {
            try {
                log.info("Connecting to inbox: {}", username);
                store.connect(URL, username, password);
                folder = (IMAPFolder) store.getFolder("INBOX");
                folder.open(Folder.READ_WRITE);
                connected = true;
            } catch (MessagingException e) {
                log.error("Error while connecting to the email server", e);
            }
        }
    }

    @Override
    public void close() {
        try {
            log.info("Closing connection to inbox: {}", username);
            if (folder != null && folder.isOpen()) {
                folder.close(true);
            }
            store.close();
            connected = false;
        } catch (MessagingException e) {
            log.warn(e);
        }
    }

    public Message[] getMessagesFrom(final String from) {
        connect();
        log.info("Trying to find emails from [{}] at inbox [{}]", from, username);
        Message[] msg = null;
        try {
            msg = folder.search(new FromTerm(new EmailAddress(from).getAddressObject()));
        } catch (MessagingException e) {
            log.error("Error while searching for emails: {}", e.getMessage());
        }
        return msg;
    }

    public Message[] getMessagesWithSubject(final String subject) {
        connect();
        log.info("Trying to find emails with subject [{}] at inbox [{}]", subject, username);
        Message[] msg = null;
        try {
            msg = folder.search(new SubjectTerm(subject));
        } catch (MessagingException e) {
            log.error("Error while searching for emails: {}", e.getMessage());
        }
        return msg;
    }

    public Message[] getMessagesFromWithSubject(final String from, final String subject) {
        connect();
        log.info("Trying to find emails from [{}] with subject [{}] at inbox [{}]", from, subject, username);
        Message[] msg = null;
        try {
            msg = folder.search(new AndTerm(new SearchTerm[]{new FromTerm(new EmailAddress(from).getAddressObject()), new SubjectTerm(subject)}));
        } catch (MessagingException e) {
            log.error("Error while searching for emails: {}", e.getMessage());
        }
        return msg;
    }

    public Message[] getMessagesTo(final String to) {
        connect();
        log.info("Trying to find emails to [{}] at inbox [{}]", to, username);
        Message[] msg = null;
        try {
            msg = folder.search(new RecipientStringTerm(Message.RecipientType.TO, to));
        } catch (MessagingException e) {
            log.error("Error while searching for emails: {}", e.getMessage());
        }
        return msg;
    }

    public Message[] getMessagesToWithSubject(final String to, final String subject) {
        connect();
        log.info("Trying to find emails to [{}] with subject [{}] at inbox [{}]", to, subject, username);
        Message[] msg = null;
        try {
            msg = folder.search(new AndTerm(new SearchTerm[]{new RecipientStringTerm(Message.RecipientType.TO, to), new SubjectTerm(subject)}));
        } catch (MessagingException e) {
            log.error("Error while searching for emails: {}", e.getMessage());
        }
        return msg;
    }

    private String getHtmlFromMessage(final Message message) {
        String result = null;
        try {
            result = getContentFromMessage(message, "text/html");
        } catch (MessagingException | IOException e) {
            log.error("error trying to get text. assumed multipart content type with at least one plain text part: {}", e.getMessage());
        }
        return result;
    }

    private String getTextFromMessage(final Message message) {
        String result = null;
        try {
            result = getContentFromMessage(message, "text/plain");
        } catch (MessagingException | IOException e) {
            log.error("error trying to get text. assumed multipart content type with at least one plain text part", e);
        }
        return result;
    }

    // todo remove throws from signature
    private String getContentFromMessage(final Message message, final String mimeType) throws IOException, MessagingException {
        String result = null;
        if (message.isMimeType("multipart/*")) {
            final MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            final int count = mimeMultipart.getCount();

            for (int i = 0; i < count; i++) {
                final BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                if (bodyPart.isMimeType(mimeType)) {
                    result = bodyPart.getContent().toString();
                    break;
                }
                if (bodyPart.isMimeType("multipart/ALTERNATIVE")) {
                    final Multipart mp = (Multipart) bodyPart.getContent();
                    final int count2 = mp.getCount();
                    for (int j = 0; j < count2; j++) {
                        final Part bp = mp.getBodyPart(j);
                        if (bp.isMimeType(mimeType)) {
                            result = (String) bp.getContent();
                            break;
                        }
                    }
                }
            }
        }
        if (message.isMimeType("text/plain")) {
            result = (String) message.getContent();
        }
        return result;
    }
}
