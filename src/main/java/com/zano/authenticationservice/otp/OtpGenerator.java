package com.zano.authenticationservice.otp;

import java.time.Clock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zano.authenticationservice.commons.RandomCodeGenerator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OtpGenerator {
    private final OtpRepository otpRepository;
    private final Clock clock;
    @Value("${user.sign-in.otp.expirtation.seconds}")
    private int otpExpiration;

    @Value("${user.sign-in.otp.length}")
    private int codeLength;
    private final RandomCodeGenerator randomCodeGenerator;

    public Otp generateOtpForEmail(String email) {
        var code = randomCodeGenerator.generateRandomCode(codeLength);
        var now = clock.instant();
        var expiry = now.plusSeconds(otpExpiration);
        var otp = new Otp(email, code, now, expiry);
        otpRepository.saveAndFlush(otp);
        return otp;
    }
}
