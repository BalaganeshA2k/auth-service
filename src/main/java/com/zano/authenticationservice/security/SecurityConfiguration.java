package com.zano.authenticationservice.security;

import static com.zano.authenticationservice.ApplicationRoles.ROLE_ADMIN;
import static com.zano.authenticationservice.ApplicationRoles.ROLE_NEW_USER;
import static com.zano.authenticationservice.ApplicationRoles.ROLE_USER;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import com.zano.authenticationservice.security.filters.SignedInUserAuthenticationFilter;
import com.zano.authenticationservice.security.filters.UnSignedInUserAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final SecurityUserDetailsService applicationUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UnSignedInUserAuthenticationFilter unSignedUserAuthenticationFilter;
    private final SignedInUserAuthenticationFilter signedUserAuthenticationFilter;
    private AuthenticationEntryPoint authenticationEntryPoint = (request,
            response, ex) -> response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    ex.getMessage());

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())

                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(this.authenticationProvider())
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/").permitAll()
                                .requestMatchers(POST, "/api/v1/users/email-id").permitAll()
                                .requestMatchers(GET, "/api/v1/user/otp/authentication").permitAll()
                                .requestMatchers(POST, "/api/v1/user/password/authentication").permitAll()
                                .requestMatchers(GET, "/api/v1/user/authorities").permitAll()
                                .requestMatchers(GET, "/api/v1/user/authentication/validation").permitAll()
                                .requestMatchers(GET, "/api/v1/users/usernames/availability")
                                .hasAnyAuthority(ROLE_NEW_USER.name())
                                .requestMatchers(POST, "/api/v1/user").hasAnyAuthority(ROLE_NEW_USER.name())
                                .requestMatchers(GET, "/api/v1/user/token/authentication")
                                .hasAnyAuthority(ROLE_USER.name())
                                .anyRequest().authenticated())
                .exceptionHandling(handling -> handling.authenticationEntryPoint(authenticationEntryPoint))
                .addFilterBefore(unSignedUserAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(signedUserAuthenticationFilter, UnSignedInUserAuthenticationFilter.class);
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
