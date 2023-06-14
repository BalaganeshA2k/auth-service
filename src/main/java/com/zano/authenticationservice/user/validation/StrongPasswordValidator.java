package com.zano.authenticationservice.user.validation;

import java.util.List;

import org.passay.PasswordData;
import org.passay.Rule;
import org.springframework.stereotype.Component;

import com.zano.authenticationservice.user.validation.annotation.StrongPassword;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@RequiredArgsConstructor
@Setter
public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    private List<Rule> passwordRules;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        var passwordData = new PasswordData(password);
        return passwordRules.stream()
                .map(rule -> rule.validate(passwordData))
                .allMatch(result -> result.isValid());
    }

}
