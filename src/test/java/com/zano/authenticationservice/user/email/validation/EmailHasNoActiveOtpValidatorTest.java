package com.zano.authenticationservice.user.email.validation;

import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.zano.authenticationservice.otp.OtpValidator;
import com.zano.authenticationservice.user.email.validation.UserEmailHasNoActiveOtpValidator;

@ExtendWith(MockitoExtension.class)
public class EmailHasNoActiveOtpValidatorTest {

    @Mock
    OtpValidator otpValidator;
    @InjectMocks
    UserEmailHasNoActiveOtpValidator emailHasNoActiveOtpValidator;

    private String email = "email@xmail.com";

    @Test
    void isValidShouldReturnTrueIfEmailHasNoActiveOtp() {
        when(otpValidator.isEmailHasNoActiveOtp(email))
            .thenReturn(true);
        Assertions.assertThat(emailHasNoActiveOtpValidator.isValid(email, null))
            .isTrue();
    }

    @Test
    void isValidShouldReturnFalseWhenEmailHasActiveOtp() {
        when(otpValidator.isEmailHasNoActiveOtp(email))
            .thenReturn(false);
        Assertions.assertThat(emailHasNoActiveOtpValidator.isValid(email, null))
            .isFalse();
    }

}
