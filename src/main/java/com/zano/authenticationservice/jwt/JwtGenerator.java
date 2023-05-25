package com.zano.authenticationservice.jwt;

import java.time.Clock;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zano.authenticationservice.user.dto.UserAuthentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtGenerator {

  @Value("${application.security.jwt.expiration.day}")
  private byte jwtExpiration;
  @Value("${spring.application.name}")
  private String issuer;
  private final Clock clock;
  private final JwtSigningKeySupplier signKeySupplier;

  public UserAuthentication generateAuthenticationToken(String email) {
    var tokenDate = getIssuedAndExpiration();
    var token = generateToken(email, tokenDate, Map.of());
    return new UserAuthentication(token, tokenDate.expiration.toString(), tokenDate.issued().toString(), issuer);
  }

  private String generateToken(String subject, IssuedAndExpiration tokenDate, Map<String, Object> claims) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(tokenDate.issued())
        .setExpiration(tokenDate.expiration())
        .setIssuer(issuer)
        .signWith(signKeySupplier.get(), SignatureAlgorithm.HS256)
        .compact();
  }

  private IssuedAndExpiration getIssuedAndExpiration() {
    var now = clock.instant();
    var expiration = now.plus(Duration.ofDays(jwtExpiration));
    var currentDate = Date.from(now);
    var expirationDate = Date.from(expiration);
    return new IssuedAndExpiration(currentDate, expirationDate);

  }

  private record IssuedAndExpiration(Date issued, Date expiration) {
  }

}
