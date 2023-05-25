package com.zano.authenticationservice.otp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, String> {
    Boolean findByEmailAndCode(String email, String code);

}
