package com.zano.authenticationservice.email.validation;

import org.springframework.stereotype.Component;

import com.zano.authenticationservice.otp.TotpService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailHasNoActiveOtpValidator implements ConstraintValidator<EmailHasNoActiveOtp, String> {
    private final TotpService totpService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return totpService.isEmailHasNoActiveOtp(email);
    }

}
