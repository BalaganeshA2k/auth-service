package com.zano.authenticationservice.user.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public record EmailPasswordRequest(String email, String password) {

    public Authentication toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

}
