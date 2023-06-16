package com.zano.authenticationservice.otp;

import java.time.Clock;

import org.springframework.stereotype.Component;

import com.zano.authenticationservice.otp.exception.OtpNotGeneratedException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OtpValidator {
    private final OtpRepository otpRepository;
    private final Clock clock;

    public enum OtpValidationResult {
        EMAIL_AND_OTP_DOES_NOT_MATCH,
        OTP_EXPRIRED,
        VALIDATION_SUCCESS;
    }

    public boolean isEmailHasNoActiveOtp(String email) {
        return otpRepository.findById(email)
                .map(this::isExpired)
                .orElse(true);
    }

    private boolean isExpired(Otp otp) {
        var now = clock.instant();
        return otp.getExpireAt()
                .isBefore(now);
    }

    public OtpValidationResult validate(String emailId, String otp) {
        var repoOtp = otpRepository.findById(emailId)
                .orElseThrow(OtpNotGeneratedException::new);
        if (!repoOtp.getCode().equals(otp))
            return OtpValidationResult.EMAIL_AND_OTP_DOES_NOT_MATCH;
        if (isExpired(repoOtp))
            return OtpValidationResult.OTP_EXPRIRED;
        otpRepository.deleteById(emailId);
        return OtpValidationResult.VALIDATION_SUCCESS;
    }

}
