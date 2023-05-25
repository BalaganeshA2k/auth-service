package com.zano.authenticationservice.otp;

import java.time.Clock;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TotpValidator {
    private final TotpRepository totpRepository;
    private final Clock clock;

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

}
