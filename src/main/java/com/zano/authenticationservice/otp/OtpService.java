package com.zano.authenticationservice.otp;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.zano.authenticationservice.user.dto.EmailRegistration;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpGenerator otpGenerator;
    private final ApplicationEventPublisher applicationEventPublisher;

    record OtpGeneratedEvent(Otp otp) {
    }

    public String generateAndSendOtp(EmailRegistration emailRegistration) {
        var otp = otpGenerator.generateOtpForEmail(emailRegistration.emailId());
        applicationEventPublisher.publishEvent(new OtpGeneratedEvent(otp));
        return "Otp is sent";
    }

}
