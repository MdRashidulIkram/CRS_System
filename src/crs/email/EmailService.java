package crs.email;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailService {

    private final String fromEmail = EmailConfig.SYSTEM_EMAIL;
    private final String fromPassword = EmailConfig.SYSTEM_EMAIL_PASSWORD;

    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, fromPassword);
            }
        });
    }

    public boolean sendEmail(String to, String subject, String body) {
        try {
            Message msg = new MimeMessage(createSession());
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(subject);
            msg.setText(body);

            Transport.send(msg);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendEmailWithAttachment(String to, String subject, String body, String filePath) {
        try {
            Message msg = new MimeMessage(createSession());
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(subject);

            // Body text
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(body);

            // Attachment
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(filePath);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            msg.setContent(multipart);

            Transport.send(msg);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
