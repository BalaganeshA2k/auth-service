package com.zano.authenticationservice.otp;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zano.authenticationservice.authentication.UserAuthentication;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OtpController {
    private final TotpService totpService;

    @PostMapping(value = "/api/v1/user/otp")
    public UserAuthentication postMethodName(@RequestBody @Valid TotpRequest otpRequest) {
        return totpService.getUserAuthentication(otpRequest);
    }

}
