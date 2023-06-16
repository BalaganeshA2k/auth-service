package com.zano.authenticationservice.user;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.ACCEPTED;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zano.authenticationservice.otp.OtpService;
import com.zano.authenticationservice.user.dto.EmailRegistration;
import com.zano.authenticationservice.user.dto.NewUser;
import com.zano.authenticationservice.user.validation.annotation.UnRegisteredEmailInHeader;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@Validated
@RequiredArgsConstructor
public class UserController {
    private final UserDetailService userDetailService;
    private final OtpService otpService;

    @GetMapping(value = "/api/v1/user/availability/{username}")
    public ResponseEntity<Boolean> getAvailabilityOfUserName(@PathVariable String username) {
        var availability = userDetailService.getAvailabilityOfUserName(username);
        return ResponseEntity.ok().body(availability);
    }

    @PostMapping(value = "/api/v1/user")
    @ResponseStatus(ACCEPTED)
    public UserDetail saveNewUser(
            @Valid @RequestBody NewUser user,
            @Valid @UnRegisteredEmailInHeader @RequestHeader(AUTHORIZATION) String authorisationHeaderValue) {
        return userDetailService.saveNewUser(user, authorisationHeaderValue);
    }

    @PostMapping("/api/v1/user/email-id/registration")
    public String sendOtpToEmailId(@Valid @RequestBody EmailRegistration emailRegistration) {
        return otpService.generateAndSendOtp(emailRegistration);
    }

}
