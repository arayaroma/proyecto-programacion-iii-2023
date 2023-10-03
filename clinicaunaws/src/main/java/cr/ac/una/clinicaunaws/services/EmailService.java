package cr.ac.una.clinicaunaws.services;

import java.io.IOException;

import cr.ac.una.clinicaunaws.dto.UserDto;
import cr.ac.una.clinicaunaws.util.Constants;
import cr.ac.una.clinicaunaws.util.HtmlFileReader;
import cr.ac.una.clinicaunaws.util.LinkGenerator;
import jakarta.annotation.Resource;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * 
 * @author arayaroma
 */
@Stateless
@LocalBean
public class EmailService {

    @Resource(name = "mail/EvaComMailSession")
    private Session mailSession;

    private void send(String to, String subject, String body) throws MessagingException {
        try {
            Message message = new MimeMessage(mailSession);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(body, "text/html");
            mailSession.getProperties().setProperty("mail.smtp.user", Constants.MAIL_USER);
            mailSession.getProperties().setProperty("mail.smtp.password", Constants.MAIL_PASSWORD);
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new MessagingException("Failed to send email: " + e.getMessage(), e);
        }
    }

    public void sendActivationHashLink(UserDto to) throws MessagingException {
        try {
            String subject = "Complete your account registration";
            String soapRequestContent = HtmlFileReader.readHtmlFromWebApp("activation.html");
            soapRequestContent = soapRequestContent.replace("{hash}", to.getActivationCode());
            String activationLink = LinkGenerator.generateActivationLink(to.getActivationCode());
            System.out.println(activationLink);
            send(
                    to.getEmail(),
                    subject,
                    HtmlFileReader.readEmailTemplate(
                            subject,
                            "Welcome to " + Constants.COMPANY_NAME + "!",
                            to.getName(),
                            "<a href=" + activationLink + ">" +
                                    "Click here to activate your account" +
                                    "</a>",
                            "Thank you for registering with us!"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new MessagingException("Failed to send email: " + e.getMessage(), e);
        }
    }

    public void sendUserActivated(UserDto to) throws MessagingException {
        try {
            String subject = "Account activated successfully";
            send(
                    to.getEmail(),
                    subject,
                    HtmlFileReader.readEmailTemplate(
                            subject,
                            "We're glad to hear from you again, ",
                            to.getName(),
                            "Feel free to explore our application and enjoy!",
                            "Thank you for choosing us!"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessagingException("Failed to send email: " + e.getMessage(), e);
        }
    }

    public void sendPasswordRecovery(UserDto to) throws MessagingException {
        try {
            String subject = "Password recovery";
            send(
                    to.getEmail(),
                    subject,
                    HtmlFileReader.readEmailTemplate(
                            subject,
                            "Don't worry we got you covered",
                            to.getName(),
                            "Login with this password: " + "<h2>" + to.getPassword()
                                    + "</h2> </br> and change it as soon as possible!",
                            "Don't share this password with anyone!"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessagingException("Failed to send email: " + e.getMessage(), e);
        }
    }

    public void sendPasswordRecovered(UserDto to) throws MessagingException {
        try {
            String subject = "Password changed successfully";
            send(
                    to.getEmail(),
                    subject,
                    HtmlFileReader.readEmailTemplate(
                            subject,
                            "We're glad you recovered your password",
                            to.getName(),
                            "Now you can login with your new password!",
                            "Thank you for choosing us!"));

        } catch (Exception e) {
            e.printStackTrace();
            throw new MessagingException("Failed to send email: " + e.getMessage(), e);
        }
    }
}