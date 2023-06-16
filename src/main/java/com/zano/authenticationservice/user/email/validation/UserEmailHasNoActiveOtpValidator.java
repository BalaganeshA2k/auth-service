package com.zano.authenticationservice.user.email.validation;

import org.springframework.stereotype.Component;

import com.zano.authenticationservice.otp.OtpValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserEmailHasNoActiveOtpValidator implements ConstraintValidator<UserEmailHasNoActiveOtp, String> {
    private final OtpValidator otpValidator;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return otpValidator.isEmailHasNoActiveOtp(email);
    }

}
