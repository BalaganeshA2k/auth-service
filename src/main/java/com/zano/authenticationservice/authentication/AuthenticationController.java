package com.zano.authenticationservice.authentication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.zano.authenticationservice.user.dto.UserAuthentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService otpService;

    @GetMapping(value = "/api/v1/authentication/otp")
    public UserAuthentication postMethodName(
            @RequestHeader("X-Email-Id") @Email @NotBlank String emailId,
            @RequestHeader("X-Otp") String otp) {
        return otpService.getUserAuthentication(emailId, otp);
    }
}
