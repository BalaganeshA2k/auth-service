package com.zano.authenticationservice.user.authentication;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.zano.authenticationservice.otp.OtpService;
import com.zano.authenticationservice.user.dto.UserAuthentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
public class UserAuthenticationController {
    private final UserAuthenticationService authenticationService;
    private final OtpService otpService;
    private final AuthenticationManager authenticationManager;

    @GetMapping(value = "/api/v1/user/otp/authentication")
    public UserAuthentication getAuthentication(
            @RequestHeader("X-Email-Id") @Email @NotBlank String emailId,
            @RequestHeader("X-Otp") String otp) {
        return authenticationService.getUserAuthentication(emailId, otp);
    }

    @GetMapping(value = "/api/v1/user/token/authentication")
    public ResponseEntity<Object> getAuthenticationByToken(@RequestHeader(AUTHORIZATION) String authHeader) {
        var authentication = authenticationService.getUserAuthentication(authHeader);
        return ResponseEntity.ok(authentication);
    }

    @PostMapping(value = "/api/v1/user/password/authentication")
    public ResponseEntity<?> sendOtpToEmail(@RequestBody EmailPasswordRequest userNamePassword) {
        authenticationManager.authenticate(userNamePassword.toAuthentication());
        otpService.generateAndSendOtp(userNamePassword.email());
        return ResponseEntity.ok("Otp will be sent to the email");
    }

    // not secured yet
    @GetMapping(value = "/api/v1/user/authentication/validation")
    public boolean getValidationForAuthenticaion(@RequestBody UserAuthentication userAuthentication) {
        return authenticationService.isSignedInUserAuthenticationTokenValid(userAuthentication.token());
    }

}
