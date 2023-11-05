package cr.ac.una.clinicaunaws.services;

import cr.ac.una.clinicaunaws.dto.MedicalAppointmentDto;
import cr.ac.una.clinicaunaws.dto.PatientCareDto;
import cr.ac.una.clinicaunaws.dto.PatientDto;
import java.io.IOException;
import cr.ac.una.clinicaunaws.dto.UserDto;
import cr.ac.una.clinicaunaws.util.Constants;
import cr.ac.una.clinicaunaws.util.HtmlFileReader;
import cr.ac.una.clinicaunaws.util.LinkGenerator;
import jakarta.activation.DataHandler;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;

/**
 *
 * @author arayaroma
 */
@Stateless
@LocalBean
public class EmailService {

    @Resource(name = "mail/ClinicaMailSession")
    private Session mailSession;

    @EJB
    PatientService pService;
    @EJB
    ReportService rService;

    private void send(String to, String subject, String body) throws MessagingException {
        try {
            Message message = new MimeMessage(mailSession);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(body, "text/html");
            mailSession.getProperties().setProperty("mail.smtp.user", Constants.MAIL_USER);
            mailSession.getProperties().setProperty("mail.smtp.password", Constants.MAIL_PASSWORD);
            mailSession.getProperties().setProperty("mail.smtp.starttls.enable", "true");
            mailSession.getProperties().setProperty("mail.smtp.auth", "true");
            mailSession.getProperties().setProperty("mail.smtp.port", "587");
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new MessagingException("Failed to send email: " + e.getMessage(), e);
        }
    }

    private void sendPdf(String to, String subject, String body, byte[] pdfBytes) throws MessagingException {
        try {
            Message message = new MimeMessage(mailSession);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            // Create the message body
            Multipart multipart = new MimeMultipart();

            // Text body part
            BodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setContent(body, "text/html");
            multipart.addBodyPart(textBodyPart);

            // PDF attachment
            if (pdfBytes != null) {
                BodyPart pdfBodyPart = new MimeBodyPart();
                pdfBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(pdfBytes, "application/pdf")));
                pdfBodyPart.setFileName("document.pdf"); // Change the filename as needed
                multipart.addBodyPart(pdfBodyPart);
            }

            // Set the message content
            message.setContent(multipart);

            mailSession.getProperties().setProperty("mail.smtp.user", Constants.MAIL_USER);
            mailSession.getProperties().setProperty("mail.smtp.password", Constants.MAIL_PASSWORD);
            mailSession.getProperties().setProperty("mail.smtp.starttls.enable", "true");
            mailSession.getProperties().setProperty("mail.smtp.auth", "true");
            mailSession.getProperties().setProperty("mail.smtp.port", "587");

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
                            "<a href=" + activationLink + ">"
                            + "Click here to activate your account"
                            + "</a>",
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

    public void sendScheduledAppointment(MedicalAppointmentDto to) throws MessagingException { //probar
        try {
            String subject = "Appointment successfully scheduled";
            send(
                    to.getPatientEmail(),
                    subject,
                    HtmlFileReader.readEmailTemplate(
                            subject,
                            "We are glad that you have scheduled with us",
                            to.getPatient().getName(),
                            "Appointment date: " + "<h2>" + to.getScheduledDate() + "</h2> </br>" + "Appointment time: " + "<h2>" + to.getScheduledStartTime() + "</h2>",
                            "Thank you for choosing us!")
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new MessagingException("Failed to send email: " + e.getMessage(), e);
        }
    }

    public void sendAppointmentReminder(MedicalAppointmentDto to) throws MessagingException {//se llama en el scheduler
        try {
            String subject = "Appointment reminder";
            send(
                    to.getPatientEmail(),
                    subject,
                    HtmlFileReader.readEmailTemplate(
                            subject,
                            "We are glad that you have scheduled with us",
                            to.getPatient().getName(),
                            "This is a reminder of your appointment with us tomorrow!"+
                                    "Appointment date: " + "<h2>" + to.getScheduledDate() + "</h2> </br>" + "Appointment time: " + "<h2>" + to.getScheduledStartTime() + "</h2>",
                            "Thank you for choosing us!"));

        } catch (Exception e) {
            e.printStackTrace();
            throw new MessagingException("Failed to send email: " + e.getMessage(), e);
        }
    }

    public void sendAppointmentResults(PatientCareDto p) throws MessagingException {
        try {
            PatientDto pat = (PatientDto) pService.getPatientById(p.getPatientHistory().getId()).getData();
            String subject = "Appointment results";
            sendPdf(
                    pat.getEmail(),
                    subject,
                    HtmlFileReader.readEmailTemplate(
                            subject,
                            "We are glad you chose us",
                            pat.getName(),
                            "Here you have the result of your appointment." + "<br>"
                            + "Blood pressure: " + p.getBloodPressure() + "<br>"
                            + "Hearth rate: " + p.getHeartRate() + "<br>"
                            + "Height: " + p.getHeight() + "<br>"
                            + "Weight: " + p.getWeight() + "<br>"
                            + "Body mass index: " + p.getBodyMassIndex() + "<br>"
                            + "We attach your medical record to this email.",
                             "Thank you for choosing us!"),
                    (byte[]) rService.createPatientReport(pat.getId()).getData());

        } catch (Exception e) {
            e.printStackTrace();
            throw new MessagingException("Failed to send email: " + e.getMessage(), e);
        }
    }
}
