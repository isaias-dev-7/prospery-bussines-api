package com.isaias.prospery_bussines_api.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.isaias.prospery_bussines_api.common.dtos.MailDto;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String email, String message, String subject) {
        try {
            MailDto mailDto = toMail(email, message, subject);
            if(mailDto == null) return;

            SimpleMailMessage m = new SimpleMailMessage();
            m.setText(mailDto.getMessage());
            m.setTo(mailDto.getTo());
            m.setSubject(mailDto.getSubject());
            m.setFrom(from);

            javaMailSender.send(m);
        } catch (Exception e) {
            handleException(e, "sendMail");
        }
    }

    private MailDto toMail(String email, String message, String subject) {
        try {
            MailDto mailDto = new MailDto();
            mailDto.setMessage(message);
            mailDto.setSubject(subject);
            mailDto.setTo(email);
            return mailDto;
        } catch (Exception e) {
            handleException(e, "userToMail");
            return null;
        }
    }

    private void handleException(Throwable error, String function){
        System.out.println("[ERROR] -  /mail/MailService: " + function);
        System.out.println(error.getMessage());
    }
}
