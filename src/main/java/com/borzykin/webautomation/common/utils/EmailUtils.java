package com.borzykin.webautomation.common.utils;

import javax.mail.BodyPart;
import javax.mail.Flags;
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
import javax.mail.search.FlagTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.RecipientStringTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import com.borzykin.webautomation.common.utils.model.Address;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPSSLStore;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EmailUtils implements AutoCloseable {
    protected Session session;
    protected Store store;
    protected IMAPFolder folder;
    protected String username;
    protected String password;
    private boolean connected = false;
    private static final String URL = "imap.gmail.com";
    private static final int PORT = 993;
    private static final String PROTOCOL = "imaps";

    public EmailUtils(String username, String password) {
        this.username = username;
        this.password = password;

        Properties props = new Properties();
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

        URLName urlName = new URLName(PROTOCOL, URL, PORT, null, username, password);
        store = new IMAPSSLStore(session, urlName);
    }

    @Override
    public void close() {
        try {
            if (folder != null && folder.isOpen()) {
                folder.close(true);
            }
            store.close();
            connected = false;
        } catch (MessagingException e) {
            log.warn(e);
        }
    }

    private void connect() {
        if (!connected) {
            try {
                store.connect(URL, username, password);
                folder = (IMAPFolder) store.getFolder("INBOX");
                folder.open(Folder.READ_WRITE);
                connected = true;
            } catch (MessagingException e) {
                log.error("error trying to connect", e);
            }
        }
    }

    public Message[] getMessages() {
        connect();
        Message[] msg = null;
        try {
            msg = folder.getMessages();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public void getMessagesFrom(String from) {
        connect();
        Message[] msg = null;
        try {
            msg = folder.search(new FromTerm(new Address(from).getAddressObject()));
            log.info(getTextFromMessage(msg[0]));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Message[] filterEmailByUnreadSubjectAndSentTo(String email, String subject) {

        SearchTerm emailSearchTerm = new RecipientStringTerm(Message.RecipientType.TO, email);
        SearchTerm subjectSearchTerm = new SubjectTerm(subject);
        SearchTerm unreadSearchTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);

        SearchTerm searchTerm = new AndTerm(new SearchTerm[]{emailSearchTerm, subjectSearchTerm, unreadSearchTerm});

        return search(searchTerm);
    }

    private Message[] search(SearchTerm searchTerm) {
        Message[] messagesFound = null;
        try {
            if (!connected) {
                connect();
            }
            messagesFound = folder.search(searchTerm);
        } catch (MessagingException e) {
            log.error("Error searching for email", e);
        }
        return messagesFound;
    }

    private Message[] filterEmailByUnreadSubject(String subject) {

        SearchTerm subjectSearchTerm = new SubjectTerm(subject);
        SearchTerm unreadSearchTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);

        SearchTerm searchTerm = new AndTerm(new SearchTerm[] { subjectSearchTerm, unreadSearchTerm });

        return search(searchTerm);
    }

    private String getHTMLFromMessage(Message message) {
        String result = null;

        try {
            result = getContentFromMessage(message, "text/html");
        } catch (MessagingException | IOException e) {
            log.error("error trying to get text. assumed multipart content type with at least one plain text part", e);
            e.printStackTrace();
        }

        return result;
    }

    private String getTextFromMessage(Message message) {

        String result = null;

        try {
            result = getContentFromMessage(message, "text/plain");
        } catch (MessagingException | IOException e) {
            log.error("error trying to get text. assumed multipart content type with at least one plain text part", e);
        }

        return result;

    }

    private String getContentFromMessage(Message message, String mimeType) throws IOException, MessagingException {

        String result = null;

        if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            int count = mimeMultipart.getCount();

            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                if (bodyPart.isMimeType(mimeType)) {
                    result = bodyPart.getContent().toString();
                    break;
                }
                if (bodyPart.isMimeType("multipart/ALTERNATIVE")) {
                    Multipart mp = (Multipart) bodyPart.getContent();
                    int count2 = mp.getCount();
                    for (int j = 0; j < count2; j++) {
                        Part bp = mp.getBodyPart(j);
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
