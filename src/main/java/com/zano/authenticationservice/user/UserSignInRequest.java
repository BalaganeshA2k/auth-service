package com.zano.authenticationservice.user;

import com.zano.authenticationservice.user.validation.UniqueUserEmail;

import jakarta.validation.constraints.Email;

public record UserSignInRequest(
                @Email(message = "Invalid Email format") @UniqueUserEmail String email) {
}
