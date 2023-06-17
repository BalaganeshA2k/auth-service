package com.zano.authenticationservice.user.authentication;

import java.time.temporal.ChronoUnit;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.zano.authenticationservice.jwt.JwtDetailsExtractor;
import com.zano.authenticationservice.jwt.JwtGenerator;
import com.zano.authenticationservice.jwt.UserAuthenticationEvent;
import com.zano.authenticationservice.otp.OtpValidator;
import com.zano.authenticationservice.otp.exception.EmailAndOtpDoesNotMatchException;
import com.zano.authenticationservice.otp.exception.OtpExpiredException;
import com.zano.authenticationservice.user.UserDetailRepository;
import com.zano.authenticationservice.user.dto.UserAuthentication;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService {
    private final OtpValidator otpValidator;
    private final JwtDetailsExtractor jwtDetailsExtractor;
    private final UserAuthenticationEventRepository userAuthenticationRepository;
    private final JwtGenerator jwtGenerator;
    private final UserDetailRepository userDetailRepository;

    public UserAuthentication getUserAuthentication(String emailId, String otp) {
        return switch (otpValidator.validate(emailId, otp)) {
            case EMAIL_AND_OTP_DOES_NOT_MATCH ->
                throw new EmailAndOtpDoesNotMatchException();
            case OTP_EXPRIRED -> throw new OtpExpiredException();
            case VALIDATION_SUCCESS -> jwtGenerator.generateAuthenticationToken(emailId);
        };
    }

    public boolean isSignedInUserAuthenticationTokenValid(String token) {
        final String email = jwtDetailsExtractor.extractSubject(token);
        return (userDetailRepository.existsByUserEmail(email))
                && isValidUserAuthentication(token);
    }

    @EventListener
    @Async
    public void userAuthenticationGeneratedEvent(UserAuthenticationEvent userAuthenticationEvent) {
        userAuthenticationRepository.save(userAuthenticationEvent);
    }

    public boolean isValidUserAuthentication(String token) {
        var email = jwtDetailsExtractor.extractSubject(token);
        var expiration = jwtDetailsExtractor.extractClaim(token, Claims::getExpiration).toInstant()
                .truncatedTo(ChronoUnit.SECONDS);
        var issued = jwtDetailsExtractor.extractClaim(token, Claims::getIssuedAt).toInstant()
                .truncatedTo(ChronoUnit.SECONDS);
        var userAuthOpt = userAuthenticationRepository.findById(email);
        if (!userAuthOpt.isPresent())
            return false;
        var userAuth = userAuthOpt.get();
        var authExpiration = userAuth.getExpiration().toInstant().truncatedTo(ChronoUnit.SECONDS);
        var authIssued = userAuth.getIssued().toInstant().truncatedTo(ChronoUnit.SECONDS);
        var expirationMatch = authExpiration.compareTo(expiration) == 0;
        var issuedMatch = authIssued.compareTo(issued) == 0;
        return expirationMatch && issuedMatch;

    }

    public UserAuthentication getUserAuthentication(String authHeader) {
        String email = jwtDetailsExtractor.extractSubjectFromBearerToken(authHeader);
        return jwtGenerator.generateAuthenticationToken(email);
    }
}
