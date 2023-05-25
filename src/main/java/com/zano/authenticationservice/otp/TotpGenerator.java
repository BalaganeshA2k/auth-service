package com.zano.authenticationservice.otp;

import java.time.Clock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TotpGenerator {
    private final TotpRepository totpRepository;
    private final Clock clock;
    @Value("${user.sign-in.otp.expirtation.seconds}")
    private int totpExpiration;

    @Value("${user.sign-in.otp.length}")
    private int codeLength;
    private final RandomCodeGenerator randomCodeGenerator;

    public Totp generateOtpForEmail(String email) {
        var code = randomCodeGenerator.generateRandomCode(codeLength);
        var now = clock.instant();
        var expiry = now.plusSeconds(totpExpiration);
        var totp = new Totp(email, code, now, expiry);
        totpRepository.saveAndFlush(totp);
        return totp;
    }
}
