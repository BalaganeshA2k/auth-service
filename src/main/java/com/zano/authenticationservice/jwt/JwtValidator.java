package com.zano.authenticationservice.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zano.authenticationservice.user.UserDetailRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtValidator {
  private final JwtDetailsExtractor jwtDetailsExtractor;
  private final UserDetailRepository userDetailRepository;

  @Value("${application.security.jwt.expiration.day}")
  private long jwtExpiration;

  public boolean isTokenValid(String token) {
    final String username = jwtDetailsExtractor.extractSubject(token);
    return (userDetailRepository.existsByUserEmail(username)) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return jwtDetailsExtractor.extractExpiration(token).before(new Date());
  }

}
