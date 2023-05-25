package com.zano.authenticationservice.user.registration;

import com.zano.authenticationservice.user.validation.UniqueUserEmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserSignInRequest(
        @Email(message = "Invalid Email format") @UniqueUserEmail @NotEmpty(message = "email should not be empty") String email) {
}
