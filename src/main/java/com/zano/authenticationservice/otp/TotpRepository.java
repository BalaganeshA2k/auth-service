package com.zano.authenticationservice.otp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TotpRepository extends JpaRepository<Totp, String> {
    Boolean findByEmailAndCode(String email, String code);

}
