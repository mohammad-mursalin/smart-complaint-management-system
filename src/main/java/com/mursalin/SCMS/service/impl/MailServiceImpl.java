package com.mursalin.SCMS.service.impl;

import com.mursalin.SCMS.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    public static final String NEW_USER_ACCOUNT_VERIFICATION = "new user account verification";

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Value("${spring.mail.verify.host}")
    private String host;


    @Override
    @Async
    public void sendSimpleMail(String name, String emailTo, String token) {

        try {

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(emailFrom);
            mailMessage.setTo(emailTo);
            mailMessage.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            mailMessage.setText(simpleMailText(name, host, token));

            mailSender.send(mailMessage);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    private String simpleMailText(String name, String host, String token) {
        return "Hello " + name + ",\n\nYour new account has been created. Please click the link below to verify your account. \n\n" +
                getVerificationUrl(host, token) + "\n\nThe support Team";
    }

    private String getVerificationUrl(String host, String token) {
        return host + "/SCMS/newUser?token=" + token;
    }
}
