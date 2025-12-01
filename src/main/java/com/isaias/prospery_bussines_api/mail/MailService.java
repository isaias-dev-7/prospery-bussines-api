package com.isaias.prospery_bussines_api.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.isaias.prospery_bussines_api.common.dtos.MailDto;
import com.isaias.prospery_bussines_api.user.entity.UserEntity;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(UserEntity user) {
        try {
            MailDto mailDto = userToMail(user);
            if(mailDto == null) return;

            SimpleMailMessage message = new SimpleMailMessage();
            message.setText(mailDto.getMessage());
            message.setTo(mailDto.getTo());
            message.setSubject(mailDto.getSubject());
            message.setFrom(from);

            javaMailSender.send(message);
        } catch (Exception e) {
            handleException(e, "sendMail");
        }
    }

    private MailDto userToMail(UserEntity user) {
        try {
            MailDto mailDto = new MailDto();
            mailDto.setMessage(user.getVerificationCode());
            mailDto.setSubject("Activation Code");
            mailDto.setTo(user.getEmail());
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
