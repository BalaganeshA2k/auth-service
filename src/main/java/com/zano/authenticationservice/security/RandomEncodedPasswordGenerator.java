package com.zano.authenticationservice.security;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RandomEncodedPasswordGenerator {
    private final PasswordEncoder passwordEncoder;

    public String generate() {
        var password = UUID.randomUUID().toString();
        return passwordEncoder.encode(password);
    }

}
