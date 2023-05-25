package com.zano.authenticationservice.jwt;

import org.springframework.stereotype.Service;

import com.zano.authenticationservice.authentication.UserAuthentication;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtGenerator jwtGenerator;

    public UserAuthentication generateAuthenticationToken(String email) {
        return jwtGenerator.generateAuthenticationToken(email);
    }

}
