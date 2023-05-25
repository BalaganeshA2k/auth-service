package com.zano.authenticationservice.email.validation;

import org.springframework.stereotype.Component;

import com.zano.authenticationservice.otp.OtpService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailHasNoActiveOtpValidator implements ConstraintValidator<EmailHasNoActiveOtp, String> {
    private final OtpService otpService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return otpService.isEmailHasNoActiveOtp(email);
    }

}
