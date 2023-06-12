package com.zano.authenticationservice.email.validation;

import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.zano.authenticationservice.otp.OtpService;

@ExtendWith(MockitoExtension.class)
public class EmailHasNoActiveOtpValidatorTest {

    @Mock
    OtpService otpService;
    @InjectMocks
    EmailHasNoActiveOtpValidator emailHasNoActiveOtpValidator;

    private String email = "email@xmail.com";

    @Test
    void isValidShouldReturnTrueIfEmailHasNoActiveOtp() {
        when(otpService.isEmailHasNoActiveOtp(email))
            .thenReturn(true);
        Assertions.assertThat(emailHasNoActiveOtpValidator.isValid(email, null))
            .isTrue();
    }

    @Test
    void isValidShouldReturnFalseWhenEmailHasActiveOtp() {
        when(otpService.isEmailHasNoActiveOtp(email))
            .thenReturn(false);
        Assertions.assertThat(emailHasNoActiveOtpValidator.isValid(email, null))
            .isFalse();
    }

}
