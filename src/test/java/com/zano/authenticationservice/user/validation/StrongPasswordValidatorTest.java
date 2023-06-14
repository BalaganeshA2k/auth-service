package com.zano.authenticationservice.user.validation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.passay.PasswordData;
import org.passay.Rule;
import org.passay.RuleResult;

import jakarta.validation.ConstraintValidatorContext;

@ExtendWith(MockitoExtension.class)
public class StrongPasswordValidatorTest {
    @Mock
    private Rule lengthRule;
    @Mock
    private Rule characterRule;
    @Spy
    private List<Rule> validationRules;
    @InjectMocks
    private StrongPasswordValidator strongPasswordValidator;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    void setUp() {
        validationRules = new ArrayList<>();
        validationRules.add(characterRule);
        validationRules.add(lengthRule);
        strongPasswordValidator.setPasswordRules(validationRules);
    }

    private String password = "password";

    RuleResult trueResult = new RuleResult(true);

    @Test
    void isValidShouldReturnTrueWhenAllRulesPass() {
        when(characterRule.validate(any(PasswordData.class)))
                .thenReturn(trueResult);
        when(lengthRule.validate(any(PasswordData.class)))
                .thenReturn(trueResult);
        Assertions.assertThat(strongPasswordValidator.isValid(password, constraintValidatorContext))
                .isTrue();
    }

    RuleResult falseResult = new RuleResult(false);

    @Test
    void isValidShouldReturnFalseIfAnyRulesFail() {
        when(characterRule.validate(any(PasswordData.class)))
                .thenReturn(trueResult);
        when(lengthRule.validate(any(PasswordData.class)))
                .thenReturn(falseResult);
        Assertions.assertThat(strongPasswordValidator.isValid(password, constraintValidatorContext))
                .isFalse();
    }
}
