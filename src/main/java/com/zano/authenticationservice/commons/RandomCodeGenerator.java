package com.zano.authenticationservice.commons;

import java.util.Random;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RandomCodeGenerator {
    public final Random random;

    public String generateRandomCode(int codeLength) {
        int max = (int) Math.pow(10, codeLength);
        int code = random.nextInt(max);
        var codeFormat = "%0" + codeLength + "d";
        return String.format(codeFormat, code);
    }
}
