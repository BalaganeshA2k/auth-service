package com.zano.authenticationservice.jwt;

import org.springframework.stereotype.Service;

import com.zano.authenticationservice.authentication.UserAuthentication;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtGenerator jwtGenerator;
    private final JwtDetailsExtractor jwtDetailsExtractor;

    public UserAuthentication generateAuthenticationToken(String email) {
        return jwtGenerator.generateAuthenticationToken(email);
    }

    public String extractSubjectFromBearerToken(String headerValue) {
        if (!headerValue.startsWith("Bearer "))
            throw new AuthorisationHeaderHasNoBearer();
        return jwtDetailsExtractor.extractSubject(headerValue.substring(7));
    }

}
