package com.zano.authenticationservice.security.filters;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zano.authenticationservice.jwt.JwtDetailsExtractor;
import com.zano.authenticationservice.security.SecurityUserDetailsService;
import com.zano.authenticationservice.user.authentication.UserAuthenticationService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SignedInUserAuthenticationFilter extends OncePerRequestFilter {
    private final SecurityUserDetailsService securityUserDetailsService;
    private final JwtDetailsExtractor jwtDetailsExtractor;
    private final UserAuthenticationService userAuthenticationService;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;

        }
        var header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null) {
            filterChain.doFilter(request, response);
            return;
        }
        var email = jwtDetailsExtractor.extractSubjectFromBearerToken(header);
        var jwt = jwtDetailsExtractor.extractTokenFromAuthorisationHeader(header);
        if (!userAuthenticationService.isSignedInUserAuthenticationTokenValid(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }
        var userDetails = securityUserDetailsService.loadUserByUsername(email);
        securityUserDetailsService.setAuthenticationToContext(request, userDetails);

        filterChain.doFilter(request, response);
    }

}
