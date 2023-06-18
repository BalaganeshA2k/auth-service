package com.zano.authenticationservice.authority;

import com.zano.authenticationservice.user.authority.UserAuthority;

public record AuthorityRequest(String authorityName) {

    public UserAuthority toUserAuthority() {
        return UserAuthority
                .builder()
                .authority(this.authorityName)
                .build();
    }

}
