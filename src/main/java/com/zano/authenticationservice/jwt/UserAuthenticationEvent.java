package com.zano.authenticationservice.jwt;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserAuthenticationEvent {
    @Id
    String email;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date issued;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date expiration;
}