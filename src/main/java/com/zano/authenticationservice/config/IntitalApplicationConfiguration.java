package com.zano.authenticationservice.config;

import static com.zano.authenticationservice.ApplicationRoles.ROLE_ADMIN;

import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import com.zano.authenticationservice.ApplicationRoles;
import com.zano.authenticationservice.authority.Authority;
import com.zano.authenticationservice.authority.AuthorityRepository;
import com.zano.authenticationservice.user.detail.UserDetail;
import com.zano.authenticationservice.user.detail.UserDetailRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class IntitalApplicationConfiguration {
    private final UserDetailRepository userDetailRepository;
    private final AuthorityRepository authorityRepository;

    @Bean
    CommandLineRunner commandLineRunner() {
        return cliInput -> {
            authorityRepository.save(defaultUserAuthority());
            userDetailRepository.save(defaultAdminUser());
        };
    }

    @Bean
    ApplicationEventMulticaster applicationEventMulticaster() {
        var multicaster = new SimpleApplicationEventMulticaster();
        multicaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return multicaster;
    }

    private Authority defaultUserAuthority() {
        return Authority.builder()
                .name(ApplicationRoles.ROLE_USER.name())
                .build();
    }

    private UserDetail defaultAdminUser() {
        return UserDetail.builder()
                .userEmail("DEFAULT_ADMIN")
                .password("admin")
                .authorities(Set.of(defaultAdminAuthority()))
                .build();
    }

    private Authority defaultAdminAuthority() {
        return Authority.builder()
                .name(ROLE_ADMIN.name())
                .build();
    }

}
