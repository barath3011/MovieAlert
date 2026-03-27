package com.college.moviealert.service;

import org.springframework.stereotype.Service;
import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.*;

import java.util.*;

@Service
public class EmailService {

    // ✅ Get Brevo API key from environment variable
    private final String apiKey = System.getenv("BREVO_API_KEY");

    public Boolean sendEmail(String toEmail, String subject, String body) {

        if (apiKey == null) {
            throw new RuntimeException("BREVO_API_KEY environment variable not set!");
        }

        try {
            // ✅ Configure API client
            ApiClient defaultClient = Configuration.getDefaultApiClient();
            ApiKeyAuth apiKeyAuth = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
            apiKeyAuth.setApiKey(apiKey);

            // ✅ Create API instance
            TransactionalEmailsApi apiInstance = new TransactionalEmailsApi();

            // ✅ Create email object
            SendSmtpEmail email = new SendSmtpEmail();

            // Sender (must be verified in Brevo)
            email.setSender(new SendSmtpEmailSender()
                    .email("barathtech30@gmail.com")
                    .name("MovieAlert"));

            // Receiver
            email.setTo(Collections.singletonList(
                    new SendSmtpEmailTo().email(toEmail)
            ));

            // Subject & Content
            email.setSubject(subject);
            email.setHtmlContent(body);

            // ✅ Send email
            CreateSmtpEmail response = apiInstance.sendTransacEmail(email);

            System.out.println("Message ID: " + response.getMessageId());

            return true;

        } catch (Exception e) {
            System.out.println("❌ Failed to send email to: " + toEmail);

            if (e instanceof sendinblue.ApiException) {
                sendinblue.ApiException apiEx = (sendinblue.ApiException) e;
                System.out.println("Status Code: " + apiEx.getCode());
                System.out.println("Response Body: " + apiEx.getResponseBody());
            }

            e.printStackTrace();
            return false;
        }
        }
    }
