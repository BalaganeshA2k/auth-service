package com.zano.authenticationservice.user.validation;

import org.springframework.stereotype.Component;

import com.zano.authenticationservice.jwt.JwtService;
import com.zano.authenticationservice.user.UserDetailService;
import com.zano.authenticationservice.user.validation.annotation.UnRegisteredEmailInHeader;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UnRegisteredEmailInHeaderValidator implements ConstraintValidator<UnRegisteredEmailInHeader, String> {
    private final UserDetailService userDetailService;
    private final JwtService jwtService;

    @Override
    public boolean isValid(String headerValue, ConstraintValidatorContext context) {
        var email = jwtService.extractSubjectFromBearerToken(headerValue);
        return !userDetailService.isEmailRegistered(email);
    }

}
