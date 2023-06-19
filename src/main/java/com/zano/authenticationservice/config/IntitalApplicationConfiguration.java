package com.zano.authenticationservice.config;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Clock;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.zano.authenticationservice.ApplicationRoles;
import com.zano.authenticationservice.user.UserDetail;
import com.zano.authenticationservice.user.UserDetailRepository;
import com.zano.authenticationservice.user.authority.UserAuthority;
import com.zano.authenticationservice.user.authority.UserAuthorityRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class IntitalApplicationConfiguration {
    private final UserDetailRepository userDetailRepository;
    private final UserAuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${application.root.user.name:root}")
    private String username;
    @Value("${application.root.user.email}")
    private String userEmail;
    @Value("${application.root.user.password:root}")
    private String password;

    @Bean
    ApplicationEventMulticaster applicationEventMulticaster() {
        var multicaster = new SimpleApplicationEventMulticaster();
        multicaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return multicaster;
    }

    @Bean
    Random random() throws NoSuchAlgorithmException {
        return SecureRandom.getInstanceStrong();
    }

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return cliInput -> {
            this.saveApplicationAuthorityDefaultsIfNotPresent();
            this.saveRootUserIfNotPresent();
        };
    }

    private void saveRootUserIfNotPresent() {
        if (!userDetailRepository.existsByUserEmail(userEmail))
            userDetailRepository.save(rootUser());
    }

    private void saveAuthorityIfNotPresent(UserAuthority userAuthority) {
        if (!authorityRepository.existsByAuthority(userAuthority.getAuthority()))
            authorityRepository.save(userAuthority);
    }

    private void saveApplicationAuthorityDefaultsIfNotPresent() {
        Arrays.stream(ApplicationRoles.values())
                .map(authority -> UserAuthority.builder()
                        .authority(authority.name())
                        .build())
                .forEach(this::saveAuthorityIfNotPresent);
    }

    private UserDetail rootUser() {
        return UserDetail.builder()
                .username(username)
                .userEmail(userEmail)
                .password(passwordEncoder.encode(password))
                .authorities(getRootAuthority())
                .build();
    }

    private Set<UserAuthority> getRootAuthority() {
        var rootAuthority = authorityRepository.findByAuthority(ApplicationRoles.ROLE_ROOT.name()).orElseThrow();
        return Set.of(rootAuthority);
    }

}
