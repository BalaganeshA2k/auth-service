package com.zano.authenticationservice.user.authentication;

import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zano.authenticationservice.jwt.UserAuthenticationEvent;

public interface UserAuthenticationEventRepository extends JpaRepository<UserAuthenticationEvent, String> {

    Set<UserAuthenticationEvent> findByIssuedAndExpiration(Date issued, Date expiration);

}
