package com.college.moviealert.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public Boolean sendEmail(String toEmail, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // ✅ Get sender email from environment variable
            String fromEmail = System.getenv("SMTP_USERNAME");
            if (fromEmail == null) {
                throw new RuntimeException("SMTP_USERNAME environment variable not set!");
            }

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // true = HTML email

            mailSender.send(message);
            System.out.println("HTML Email sent to: " + toEmail);
            return true;
        } catch (Exception e) {
            System.out.println("Failed to send email to: " + toEmail);
            e.printStackTrace();
            return false;
        }
    }
}