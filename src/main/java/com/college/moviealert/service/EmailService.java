package com.college.moviealert.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    // ✅ Get SendGrid API key from environment variable
    private final String apiKey = System.getenv("SENDGRID_API_KEY");

    public Boolean sendEmail(String toEmail, String subject, String body) {
        if (apiKey == null) {
            throw new RuntimeException("SENDGRID_API_KEY environment variable not set!");
        }

        // ✅ Set sender email (must be verified in SendGrid)
        Email from = new Email("barathtech30@gmail.com");
        Email to = new Email(toEmail);
        Content content = new Content("text/html", body); // HTML content
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());
            System.out.println("Headers: " + response.getHeaders());

            return response.getStatusCode() >= 200 && response.getStatusCode() < 300;

        } catch (IOException ex) {
            System.out.println("Failed to send email to: " + toEmail);
            ex.printStackTrace();
            return false;
        }
    }
}