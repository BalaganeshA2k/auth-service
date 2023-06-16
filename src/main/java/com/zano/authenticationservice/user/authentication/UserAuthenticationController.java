package com.zano.authenticationservice.user.authentication;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.zano.authenticationservice.user.dto.UserAuthentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserAuthenticationController {
    private final UserAuthenticationService authenticationService;

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

}
