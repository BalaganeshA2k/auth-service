package com.zano.authenticationservice.otp;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.zano.authenticationservice.user.registration.UserSignInRequest;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TotpService {
    private final TotpGenerator totpGenerator;
    private final EmailOtpSender totOtpSender;
    private final TotpValidator totpValidator;

    @Async
    @EventListener({ UserSignInRequest.class })
    public void sendTotp(UserSignInRequest userSignInRequest) throws MessagingException {
        var totp = totpGenerator.generateOtpForEmail(userSignInRequest.email());
        totOtpSender.sendOtp(totp);
    }

    public boolean isEmailHasNoActiveOtp(String email) {
        return totpValidator.isEmailHasNoActiveOtp(email);
    }

}
