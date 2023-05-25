package com.zano.authenticationservice.user.registration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDefaultPasswordSender {
    public final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    String applicationEmail;

    @EventListener
    @Async
    public void sendDefaultPassword(UserEmailVerificationEvent userEmailVerificationEvent) {
        var mail = new SimpleMailMessage();
        mail.setSubject("Zano Password");
        mail.setFrom(applicationEmail);
        mail.setTo(userEmailVerificationEvent.email());
        mail.setText("Your Default Password is: " + userEmailVerificationEvent.password());
        javaMailSender.send(mail);
    }
}