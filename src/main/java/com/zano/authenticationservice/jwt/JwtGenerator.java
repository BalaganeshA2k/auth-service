package com.zano.authenticationservice.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.zano.authenticationservice.user.registration.UserSignInRequest;

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

  public String generateSignInJwt(UserSignInRequest userSignInRequest) {
    return buildToken(Map.of(), userSignInRequest.email());
  }

  public String generateJwt(
      Map<String, Object> extraClaims,
      UserDetails userDetails) {
    return buildToken(extraClaims, userDetails.getUsername());
  }

  private String buildToken(
      Map<String, Object> extraClaims,
      String subject) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
        .setIssuer(issuer)
        .signWith(signKeySupplier.get(), SignatureAlgorithm.HS256)
        .compact();
  }

}
