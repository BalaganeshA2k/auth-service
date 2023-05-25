package com.zano.authenticationservice.otp;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.zano.authenticationservice.authentication.UserAuthentication;
import com.zano.authenticationservice.jwt.JwtService;
import com.zano.authenticationservice.otp.exception.EmailAndOtpDoesNotMatchException;
import com.zano.authenticationservice.otp.exception.OtpExpiredException;
import com.zano.authenticationservice.user.registration.UserSignInRequest;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TotpService {
    private final TotpGenerator totpGenerator;
    private final EmailOtpSender totOtpSender;
    private final TotpValidator totpValidator;
    private final JwtService jwtService;

    @Async
    @EventListener({ UserSignInRequest.class })
    public void sendTotp(UserSignInRequest userSignInRequest) throws MessagingException {
        var totp = totpGenerator.generateOtpForEmail(userSignInRequest.email());
        totOtpSender.sendOtp(totp);
    }

    public boolean isEmailHasNoActiveOtp(String email) {
        return totpValidator.isEmailHasNoActiveOtp(email);
    }

    public UserAuthentication getUserAuthentication(TotpRequest otpRequest) {
        return switch (totpValidator.validate(otpRequest)) {
            case EMAIL_AND_OTP_DOES_NOT_MATCH ->
                throw new EmailAndOtpDoesNotMatchException();
            case OTP_EXPRIRED -> throw new OtpExpiredException();
            case VALIDATION_SUCCESS -> jwtService.generateAuthenticationToken(otpRequest.email());
        };
    }

}
