package com.zano.authenticationservice.jwt;

import lombok.RequiredArgsConstructor;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtValidator {
  private final JwtDetailsExtractor jwtDetailsExtractor;
  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = jwtDetailsExtractor.extractUsername(token);
    
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return jwtDetailsExtractor.extractExpiration(token).before(new Date());
  }

}
