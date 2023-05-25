package com.zano.authenticationservice.user.verification;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.zano.authenticationservice.jwt.JwtGenerator;
import com.zano.authenticationservice.user.UserSignInRequest;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserEmailVerifier {
  private final JwtGenerator jwtGenerator;
  private final JavaMailSender javaMailSender;
  @Value("${spring.mail.username}")
  private String applicationEmail;
  @Value("${server.port}")
  private long port;

  @EventListener
  public void verificationEmailSender(UserSignInRequest userSignInRequest)
      throws MailException, FileNotFoundException, MessagingException {
    var message = javaMailSender.createMimeMessage();
    String url = "http://localhost:" + port + "/user/verification?code="
        + jwtGenerator.generateSignInJwt(userSignInRequest);
    var helper = new MimeMessageHelper(message);
    var emailContent = signInVerificationEmailContent(userSignInRequest.email(), url);
    helper.setTo(userSignInRequest.email());
    message.setContent(emailContent, "text/html; charset=utf-8");
    helper.setFrom(applicationEmail);
    javaMailSender.send(helper.getMimeMessage());
  }

  private String signInVerificationEmailContent(String userEmail, String verificationUrl) throws FileNotFoundException {
    return new File("sign-in-email-verification.html").toString()
        .replace("${USER_EMAIL}", userEmail)
        .replace("${VERIFICATION_URL}", verificationUrl);
  }
}
