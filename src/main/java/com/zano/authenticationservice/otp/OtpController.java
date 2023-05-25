package com.zano.authenticationservice.otp;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.zano.authenticationservice.user.dto.UserAuthentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
public class OtpController {
    private final TotpService totpService;

    @PostMapping("/api/v1/otp/email-id")
    public String sendOtpToEmailId(@RequestBody OtpEmailId otpEmailId) {
        return totpService.sendTotp(otpEmailId);
    }
}
