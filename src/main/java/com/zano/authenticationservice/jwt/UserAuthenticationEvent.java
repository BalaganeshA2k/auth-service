package com.zano.authenticationservice.jwt;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticationEvent {
    @Id
    String email;
    Date issued;
    Date expiration;
}