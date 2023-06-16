package com.zano.authenticationservice.security.filters;

import static com.zano.authenticationservice.ApplicationRoles.ROLE_NEW_USER;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
public class UnSignedInUserAuthenticationFilter extends OncePerRequestFilter {
    private final JwtDetailsExtractor jwtDetailsExtractor;
    private final UserAuthenticationService authenticationService;
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
        var email = jwtDetailsExtractor.extractSubjectFromBearerToken(header);
        var token = jwtDetailsExtractor.extractTokenFromAuthorisationHeader(header);
        boolean isNotValidUserAuthentication = !authenticationService.isValidUserAuthentication(token);
        if (isNotValidUserAuthentication) {
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
