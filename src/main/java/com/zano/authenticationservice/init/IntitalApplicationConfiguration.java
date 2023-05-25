package com.zano.authenticationservice.init;

import static com.zano.authenticationservice.ApplicationRoles.ROLE_ADMIN;

import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zano.authenticationservice.ApplicationRoles;
import com.zano.authenticationservice.authority.Authority;
import com.zano.authenticationservice.authority.AuthorityRepository;
import com.zano.authenticationservice.user.UserDetail;
import com.zano.authenticationservice.user.UserDetailRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class IntitalApplicationConfiguration {
    private final UserDetailRepository userDetailRepository;
    private final AuthorityRepository authorityRepository;
    @Bean
    CommandLineRunner commandLineRunner(){
        return cliInput ->{
            authorityRepository.save(defaultUserAuthority());
            userDetailRepository.save(defaultAdminUser());
        } ;
    } 
    private Authority defaultUserAuthority(){
        return Authority.builder()
        .name(ApplicationRoles.ROLE_USER.name())
        .build();
    }  
    private UserDetail defaultAdminUser() {
        return UserDetail.builder()
        .username("DEFAULT_ADMIN")
        .password("admin")
        .authorities(Set.of(defaultAdminAuthority()))
        .build();
    }
    private Authority defaultAdminAuthority(){
        return Authority.builder()
            .name(ROLE_ADMIN.name())
            .build();
    }
    
   
    
}
