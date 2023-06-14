package com.zano.authenticationservice.security.filters;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zano.authenticationservice.jwt.JwtDetailsExtractor;
import com.zano.authenticationservice.jwt.JwtService;
import com.zano.authenticationservice.security.SecurityUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SignedInUserAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final SecurityUserDetailsService userDetailsService;
    private final JwtDetailsExtractor jwtDetailsExtractor;

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
        var jwt = jwtDetailsExtractor.extractTokenFromAuthorisationHeader(header);
        if (!jwtService.isTokenValid(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }
        var userDetails = userDetailsService.loadUserByUsername(email);
        var authentication = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), null, userDetails.getAuthorities());
        var detailSource = new WebAuthenticationDetailsSource().buildDetails(request);
        authentication.setDetails(detailSource);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

}
