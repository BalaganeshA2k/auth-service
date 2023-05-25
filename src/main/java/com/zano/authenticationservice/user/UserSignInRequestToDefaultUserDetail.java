package com.zano.authenticationservice.user;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.zano.authenticationservice.authority.AuthorityService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSignInRequestToDefaultUserDetail implements Function<UserSignInRequest, UserDetail> {
    private final AuthorityService authorityService;

    @Override
    public UserDetail apply(UserSignInRequest userSignInRequest) {
        return UserDetail.builder()
                .userEmail(userSignInRequest.email())
                .password(userSignInRequest.password())
                .authorities(authorityService.getDefaultUserAuthority())
                .build();
    }

}
