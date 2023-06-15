package com.zano.authenticationservice.otp;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
public class OtpController {
    private final OtpService otpService;

    @PostMapping("/api/v1/otp/email-id")
    public String sendOtpToEmailId(@Valid @RequestBody OtpEmailId otpEmailId) {
        return otpService.sendTotp(otpEmailId);
    }
}
