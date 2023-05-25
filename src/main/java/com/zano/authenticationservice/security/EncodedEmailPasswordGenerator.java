package com.zano.authenticationservice.security;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncodedEmailPasswordGenerator {
    private final PasswordEncoder passwordEncoder;

    public String generate(String email) {
        var password = UUID.fromString(email).toString();
        return passwordEncoder.encode(password);
    }

}
