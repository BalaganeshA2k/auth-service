package com.zano.authenticationservice.otp;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.zano.authenticationservice.jwt.JwtService;
import com.zano.authenticationservice.otp.TotpValidator.TotpValidationResult;
import com.zano.authenticationservice.otp.exception.EmailAndOtpDoesNotMatchException;
import com.zano.authenticationservice.otp.exception.OtpExpiredException;
import com.zano.authenticationservice.user.dto.UserAuthentication;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class TotpService {
    private final TotpGenerator totpGenerator;
    private final EmailOtpSender totOtpSender;
    private final TotpValidator totpValidator;
    private final ApplicationEventPublisher applicationEventPublisher;

    record OtpGeneratedEvent(Totp totp) {
    }

    public String sendTotp(OtpEmailId otpEmailId) {
        var totp = totpGenerator.generateOtpForEmail(otpEmailId.emailId());
        applicationEventPublisher.publishEvent(new OtpGeneratedEvent(totp));
        return "Otp is sent";
    }

    @EventListener
    @Async
    @SneakyThrows
    void sendOtpAsyncToEmail(OtpGeneratedEvent otpGeneratedEvent) {
        totOtpSender.sendOtp(otpGeneratedEvent.totp());
    }

    public boolean isEmailHasNoActiveOtp(String email) {
        return totpValidator.isEmailHasNoActiveOtp(email);
    }

    public TotpValidationResult validate(String emailId, String otp) {
        return totpValidator.validate(emailId, otp);
    }
}
