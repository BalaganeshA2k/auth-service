package com.zano.authenticationservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final SecurityUserDetailsService applicationUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtFilter jwtFilter;
    private AuthenticationEntryPoint authenticationEntryPoint = (request,
            response, ex) -> {
        response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                ex.getMessage());
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(this.authenticationProvider())
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(HttpMethod.POST, "/api/v1/otp/email-id").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/otp/authentication").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/user").permitAll()
                                .anyRequest().authenticated())
                .exceptionHandling(handling -> handling.authenticationEntryPoint(authenticationEntryPoint))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        var authenticationProvider = new DaoAuthenticationProvider(passwordEncoder);
        authenticationProvider.setUserDetailsService(applicationUserDetailsService);
        return authenticationProvider;
    }

}
