package com.zano.authenticationservice.otp;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class OtpSender {
    private final JavaMailSender javaMailSender;
    @Value("spring.mail.username")
    private String applicationEmail;

    public void sendOtp(Totp totp) throws MessagingException {
        var mail = javaMailSender.createMimeMessage();
        var helper = new MimeMessageHelper(mail);
        helper.addTo(totp.getEmail());
        helper.setFrom(applicationEmail);
        helper.setText(getOtpEmailContent(totp), true);
        helper.setSubject("ZANO Sign-in otp");
        javaMailSender.send(helper.getMimeMessage());
    }

    @SneakyThrows
    public String otpEmailTemplate() {
        var path = Paths.get(System.getProperty("user.dir") + "/src/main/resources/sign-in-email-verification.html");
        var emailContent = Files.readAllBytes(path);
        return new String(emailContent);
    }

    @SneakyThrows
    String getOtpEmailContent(Totp totp) {
        return otpEmailTemplate()
                .replace("${USER_EMAIL}", totp.getEmail())
                .replace("${TOTP}", totp.getCode())
                .replace("${TOTP_EXPIRY}", totp.getExpireAt().toString());

    }
}
