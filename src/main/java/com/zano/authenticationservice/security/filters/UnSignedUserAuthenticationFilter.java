package com.zano.authenticationservice.security.filters;

import static com.zano.authenticationservice.ApplicationRoles.ROLE_NEW_USER;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zano.authenticationservice.authentication.AuthenticationService;
import com.zano.authenticationservice.jwt.JwtService;
import com.zano.authenticationservice.security.SecurityUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UnSignedUserAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final SecurityUserDetailsService securityUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
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
        var email = jwtService.extractSubjectFromBearerToken(header);
        boolean userNotExist = !authenticationService.isUserAuthenticationExists(email);
        if (userNotExist) {
            filterChain.doFilter(request, response);
            return;
        }
        var userDetails = userDetails(email);
        securityUserDetailsService.setAuthenticationToContext(request, userDetails);
        filterChain.doFilter(request, response);
    }

    private UserDetails userDetails(String email) {
        return User.builder()
                .username(email)
                .password(email)
                .authorities(ROLE_NEW_USER.name())
                .build();
    }

}
