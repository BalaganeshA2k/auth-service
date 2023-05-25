package com.zano.authenticationservice.otp;

import com.zano.authenticationservice.email.validation.EmailHasNoActiveOtp;
import com.zano.authenticationservice.email.validation.UniqueUserEmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record OtpEmailId(
                @Email(message = "Invalid Email format") @UniqueUserEmail @EmailHasNoActiveOtp @NotEmpty(message = "email should not be empty") String emailId) {

}
