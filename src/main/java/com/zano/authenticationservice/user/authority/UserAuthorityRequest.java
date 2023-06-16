package com.zano.authenticationservice.user.authority;

public record UserAuthorityRequest(String authorityName) {

    UserAuthority toAuthority() {
        return UserAuthority.builder()
                .authority(authorityName)
                .build();
    }
}
