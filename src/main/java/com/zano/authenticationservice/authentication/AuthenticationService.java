package com.zano.authenticationservice.authentication;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.zano.authenticationservice.jwt.JwtService;
import com.zano.authenticationservice.jwt.UserAuthenticationEvent;
import com.zano.authenticationservice.otp.OtpService;
import com.zano.authenticationservice.otp.exception.EmailAndOtpDoesNotMatchException;
import com.zano.authenticationservice.otp.exception.OtpExpiredException;
import com.zano.authenticationservice.user.dto.UserAuthentication;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final OtpService otpService;
    private final JwtService jwtService;
    private final UserAuthenticationRepository userAuthenticationRepository;

    public UserAuthentication getUserAuthentication(String emailId, String otp) {
        return switch (otpService.validate(emailId, otp)) {
            case EMAIL_AND_OTP_DOES_NOT_MATCH ->
                throw new EmailAndOtpDoesNotMatchException();
            case OTP_EXPRIRED -> throw new OtpExpiredException();
            case VALIDATION_SUCCESS -> jwtService.generateAuthenticationToken(emailId);
        };
    }

    @EventListener
    @Async
    public void userAuthenticationGeneratedEvent(UserAuthenticationEvent userAuthenticationEvent) {
        userAuthenticationRepository.save(userAuthenticationEvent);
    }

    public boolean isUserAuthenticationExists(String email) {
        return userAuthenticationRepository.existsById(email);

    }
}
