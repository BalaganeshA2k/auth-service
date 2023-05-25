package com.zano.authenticationservice.user.verification;

import java.util.UUID;
import java.util.function.Function;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncodedPasswordGenerator implements Function<String, String> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String apply(String email) {
        var password = UUID.randomUUID().fromString(email).toString();
        var encodedPassword = passwordEncoder.encode(email);
        return encodedPassword;
    }

}
