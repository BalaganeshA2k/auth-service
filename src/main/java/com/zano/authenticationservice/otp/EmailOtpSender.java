package com.zano.authenticationservice.otp;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.zano.authenticationservice.otp.OtpService.OtpGeneratedEvent;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class EmailOtpSender {
    private final JavaMailSender javaMailSender;
    @Value("spring.mail.username")
    private String applicationEmail;
    @Value("${user.sign-in.otp.expirtation.seconds}")
    private long otpExpiration;

    @EventListener
    @Async
    @SneakyThrows
    void sendOtpAsyncToEmail(OtpGeneratedEvent otpGeneratedEvent) {
        sendOtp(otpGeneratedEvent.otp());
    }

    public void sendOtp(Otp otp) throws MessagingException {
        var mail = javaMailSender.createMimeMessage();
        var helper = new MimeMessageHelper(mail);
        helper.addTo(otp.getEmail());
        helper.setFrom(applicationEmail);
        helper.setText(getOtpEmailContent(otp), true);
        helper.setSubject("ZANO Sign-in otp");
        javaMailSender.send(helper.getMimeMessage());
    }

    @SneakyThrows
    private String otpEmailTemplate() {
        var path = Paths.get(System.getProperty("user.dir") + "/src/main/resources/sign-in-email-verification.html");
        var emailContent = Files.readAllBytes(path);
        return new String(emailContent);
    }

    @SneakyThrows
    String getOtpEmailContent(Otp otp) {
        return otpEmailTemplate()
                .replace("${USER_EMAIL}", otp.getEmail())
                .replace("${TOTP}", otp.getCode())
                .replace("${TOTP_EXPIRATION}", String.valueOf(otpExpiration))
                .replace("${EXPIRATION_UNIT}", "seconds");

    }
}
