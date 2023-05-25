package com.zano.authenticationservice.user.validation;

import org.springframework.stereotype.Component;

import com.zano.authenticationservice.user.detail.UserDetailRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UniqueUserEmailValidator implements ConstraintValidator<UniqueUserEmail, String> {
    private final UserDetailRepository userDetailRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userDetailRepository.existsByUserEmail(email);
    }

}
