package com.zano.authenticationservice.user.dto;

import com.zano.authenticationservice.user.email.validation.UserEmailHasNoActiveOtp;
import com.zano.authenticationservice.user.email.validation.UniqueUserEmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record EmailRegistration(
        @Email(message = "Invalid Email format") @UniqueUserEmail @UserEmailHasNoActiveOtp @NotEmpty(message = "email should not be empty") String emailId) {

}
