package com.zano.authenticationservice.user.dto;

import com.zano.authenticationservice.user.validation.annotation.StrongPassword;
import com.zano.authenticationservice.user.validation.annotation.UnRegisteredUserName;

import jakarta.validation.constraints.NotBlank;

public record NewUser(@UnRegisteredUserName @NotBlank(message = "username cannot be blank") String username,
                @StrongPassword @NotBlank(message = "password cannot be blank") String password) {
}
