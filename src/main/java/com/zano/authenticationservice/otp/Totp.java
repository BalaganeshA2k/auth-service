package com.zano.authenticationservice.otp;

import java.time.Instant;

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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Totp {
    @Id
    private String email;
    private String code;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Instant generatedAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Instant expireAt;

}
