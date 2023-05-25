package com.zano.authenticationservice.otp;

import java.time.Clock;

import org.springframework.stereotype.Component;

import com.zano.authenticationservice.otp.exception.OtpNotGeneratedException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TotpValidator {
    private final TotpRepository totpRepository;
    private final Clock clock;

    enum TotpValidationResult {
        EMAIL_AND_OTP_DOES_NOT_MATCH,
        OTP_EXPRIRED,
        VALIDATION_SUCCESS;
    }

    public boolean isEmailHasNoActiveOtp(String email) {
        return totpRepository.findById(email)
                .map(this::isExpired)
                .orElse(true);
    }

    private boolean isExpired(Totp totp) {
        var now = clock.instant();
        return totp.getExpireAt()
                .isBefore(now);
    }

    public TotpValidationResult validate(TotpRequest otpRequest) {
        var otp = totpRepository.findById(otpRequest.email())
                .orElseThrow(() -> new OtpNotGeneratedException());
        if (!otp.getCode().equals(otpRequest.otp()))
            return TotpValidationResult.EMAIL_AND_OTP_DOES_NOT_MATCH;
        if (isExpired(otp))
            return TotpValidationResult.OTP_EXPRIRED;
        totpRepository.deleteById(otpRequest.email());
        return TotpValidationResult.VALIDATION_SUCCESS;
    }

}
