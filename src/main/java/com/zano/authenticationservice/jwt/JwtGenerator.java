package com.zano.authenticationservice.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtGenerator {

  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;
  @Value("${spring.application.name}")
  private String issuer;

  private final JwtSigningKeySupplier signKeySupplier;

  public String generateJwt(UserDetails userDetails) {
    return generateJwt(new HashMap<>(), userDetails);
  }

  public String generateJwt(
      Map<String, Object> extraClaims,
      UserDetails userDetails) {
    return buildToken(extraClaims, userDetails, jwtExpiration);
  }

  private String buildToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails,
      long expiration) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .setIssuer(issuer)
        .signWith(signKeySupplier.get(), SignatureAlgorithm.HS256)
        .compact();
  }

}
