package com.zano.authenticationservice.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.zano.authenticationservice.commons.exception.UserNameNotFoundExceptionSupplier;
import com.zano.authenticationservice.user.UserDetail;
import com.zano.authenticationservice.user.UserDetailRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {
    private final UserDetailRepository userDetailRepository;
    private final UserNameNotFoundExceptionSupplier usernameNotFoundExceptionSupplier;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userDetailRepository.findOneByUserEmail(email)
                .map(this::mapToUserDetails)
                .orElseThrow(usernameNotFoundExceptionSupplier);
    }

    private UserDetails mapToUserDetails(UserDetail userDetail) {
        return User.withUsername(userDetail.getUserEmail())
                .password(userDetail.getPassword())
                .authorities(userDetail.getAuthorities())
                .build();
    }

}
