package com.zano.authenticationservice.otp;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.zano.authenticationservice.otp.OtpValidator.OtpValidationResult;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpGenerator otpGenerator;
    private final EmailOtpSender totOtpSender;
    private final OtpValidator otpValidator;
    private final ApplicationEventPublisher applicationEventPublisher;

    record OtpGeneratedEvent(Otp otp) {
    }

    public String sendTotp(OtpEmailId otpEmailId) {
        var otp = otpGenerator.generateOtpForEmail(otpEmailId.emailId());
        applicationEventPublisher.publishEvent(new OtpGeneratedEvent(otp));
        return "Otp is sent";
    }

    @EventListener
    @Async
    @SneakyThrows
    void sendOtpAsyncToEmail(OtpGeneratedEvent otpGeneratedEvent) {
        totOtpSender.sendOtp(otpGeneratedEvent.otp());
    }

    public boolean isEmailHasNoActiveOtp(String email) {
        return otpValidator.isEmailHasNoActiveOtp(email);
    }

    public OtpValidationResult validate(String emailId, String otp) {
        return otpValidator.validate(emailId, otp);
    }
}
