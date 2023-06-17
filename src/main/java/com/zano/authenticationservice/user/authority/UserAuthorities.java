package com.zano.authenticationservice.user.authority;

import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public record UserAuthorities(Set<SimpleGrantedAuthority> authorities) {

}
