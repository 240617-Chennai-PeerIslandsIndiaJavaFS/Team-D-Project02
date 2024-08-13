package com.teamD.RevTaskManagement.utilities;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class EmailService {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    RandomCredentialsGenerator generator;

    public void sendMail(String recipient, String subject, String template) throws MessagingException {
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper messageHelper=new MimeMessageHelper(mimeMessage,true,"UTF-8");
        messageHelper.setTo(recipient);
        messageHelper.setSubject(subject);
        messageHelper.setText(template,true);
        mailSender.send(mimeMessage);
    }

    public String registrationTemplate(String name, String email,String password) {
        String  companyLogoUrl= "https://ci3.googleusercontent.com/meips/ADKq_NZzzF8-J04pj06Q8pdPUclP2jrDHxdZBKibzhjNtGBENFRelzOlaBy3GTh7TQ3sYdvH9SfJrpstXYlZpQHwViBk81B8MBezznkmT90aZ639hgUzUTpgQxTgAkM62cnAIFC-U1YIpm2uu24U4tJXEyag0VdkyKpg44hy9ilf=s0-d-e1-ft#https://gallery.mailchimp.com/4614193ed19755dee2c402a7a/images/92f7d632-70a8-4300-ad34-15d869efbd12.png";
        return "<html>" +
                "<head><style>" +
                "body { font-family: Arial, sans-serif; margin: 0; padding: 0; }" +
                "h1 { color: #333; }" +
                "p { font-size: 16px; }" +
                "footer { font-size: 12px; color: #666; margin-top: 20px; }" +
                "img { max-width: 100%; height: auto; }" +
                "</style></head>" +
                "<body>" +
                "<header>" +
                "<img src=\"" + companyLogoUrl + "\" alt=\"Company Logo\" style=\"width: 200px;\">" +
                "</header>" +
                "<main>" +
                "<h1>Hello, " + name + "!</h1>" +
                "<p>We have set up your account. Below are your credentials:</p>" +
                "<p><strong>Email:</strong> " + email + "</p>" +
                "<p><strong>Password:</strong> " + password + "</p>" +
                "<p>If you have any questions, please contact our support team.</p>" +
                "<p>You can login and reset your password"+
                "</main>" +
                "<footer>" +
                "<p>&copy; 2024 Your Revature. All rights reserved.</p>" +
                "</footer>" +
                "</body></html>";
    }

    public String passwordResetTemplate(String name,String pin) {
        String companyLogoUrl = "https://ci3.googleusercontent.com/meips/ADKq_NZzzF8-J04pj06Q8pdPUclP2jrDHxdZBKibzhjNtGBENFRelzOlaBy3GTh7TQ3sYdvH9SfJrpstXYlZpQHwViBk81B8MBezznkmT90aZ639hgUzUTpgQxTgAkM62cnAIFC-U1YIpm2uu24U4tJXEyag0VdkyKpg44hy9ilf=s0-d-e1-ft#https://gallery.mailchimp.com/4614193ed19755dee2c402a7a/images/92f7d632-70a8-4300-ad34-15d869efbd12.png";
        return "<html>" +
                "<head><style>" +
                "body { font-family: Arial, sans-serif; margin: 0; padding: 0; }" +
                "h1 { color: #333; }" +
                "p { font-size: 16px; }" +
                "footer { font-size: 12px; color: #666; margin-top: 20px; }" +
                "img { max-width: 100%; height: auto; }" +
                "</style></head>" +
                "<body>" +
                "<header>" +
                "<img src=\"" + companyLogoUrl + "\" alt=\"Company Logo\" style=\"width: 200px;\">" +
                "</header>" +
                "<main>" +
                "<h1>Hello, " + name + "!</h1>" +
                "<p>We received a request to reset your password. Below is your 6-digit PIN:</p>" +
                "<p><strong>PIN:</strong> " + pin + "</p>" +
                "<p>Please enter this PIN to reset your password. If you did not request this, please ignore this email.</p>" +
                "</main>" +
                "<footer>" +
                "<p>&copy; 2024 Your Company. All rights reserved.</p>" +
                "</footer>" +
                "</body></html>";
    }
}
