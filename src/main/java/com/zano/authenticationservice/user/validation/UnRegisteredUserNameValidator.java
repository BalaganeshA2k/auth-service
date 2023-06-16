package com.zano.authenticationservice.user.validation;

import org.springframework.stereotype.Component;

import com.zano.authenticationservice.user.UserDetailService;
import com.zano.authenticationservice.user.validation.annotation.UnRegisteredUserName;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UnRegisteredUserNameValidator implements ConstraintValidator<UnRegisteredUserName, String> {
    private final UserDetailService userDetailService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return !userDetailService.getAvailabilityOfUserName(username);
    }

}
