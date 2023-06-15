package com.zano.authenticationservice.authentication;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zano.authenticationservice.jwt.UserAuthenticationEvent;

public interface UserAuthenticationRepository extends JpaRepository<UserAuthenticationEvent, String> {

}
