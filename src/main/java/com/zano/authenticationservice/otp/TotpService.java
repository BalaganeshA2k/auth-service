package com.zano.authenticationservice.otp;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.zano.authenticationservice.authentication.UserAuthentication;
import com.zano.authenticationservice.jwt.JwtService;
import com.zano.authenticationservice.otp.exception.EmailAndOtpDoesNotMatchException;
import com.zano.authenticationservice.otp.exception.OtpExpiredException;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class TotpService {
    private final TotpGenerator totpGenerator;
    private final EmailOtpSender totOtpSender;
    private final TotpValidator totpValidator;
    private final JwtService jwtService;
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

    public UserAuthentication getUserAuthentication(String emailId, String otp) {
        return switch (totpValidator.validate(emailId, otp)) {
            case EMAIL_AND_OTP_DOES_NOT_MATCH ->
                throw new EmailAndOtpDoesNotMatchException();
            case OTP_EXPRIRED -> throw new OtpExpiredException();
            case VALIDATION_SUCCESS -> jwtService.generateAuthenticationToken(emailId);
        };
    }

}
