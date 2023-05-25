package com.zano.authenticationservice.user.validation;

import java.util.List;

import org.passay.PasswordData;
import org.passay.Rule;
import org.springframework.stereotype.Component;

import com.zano.authenticationservice.user.validation.annotation.StrongPassword;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    private final List<Rule> passwordRules;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        var passwordData = new PasswordData(password);
        return passwordRules.stream()
                .map(rule -> rule.validate(passwordData))
                .allMatch(result -> result.isValid());
    }

}
