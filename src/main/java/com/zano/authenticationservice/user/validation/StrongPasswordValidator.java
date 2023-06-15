package com.zano.authenticationservice.user.validation;

import java.util.List;

import org.passay.PasswordData;
import org.passay.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zano.authenticationservice.user.validation.annotation.StrongPassword;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Setter;

@Component
@Setter
public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    @Autowired
    private List<Rule> passwordRules;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        var passwordData = new PasswordData(password);
        return passwordRules.stream()
                .map(rule -> rule.validate(passwordData))
                .allMatch(result -> result.isValid());
    }

}
