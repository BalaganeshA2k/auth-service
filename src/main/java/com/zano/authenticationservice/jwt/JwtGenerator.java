package com.zano.authenticationservice.jwt;

import java.time.Clock;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
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
  private final ApplicationEventPublisher applicationEventPublisher;

  public UserAuthentication generateUserAuthentication(String email, IssuedAndExpiration tokenDate) {
    var token = generateToken(email, tokenDate, Map.of());
    Date expiration = tokenDate.expiration();
    Date issued = tokenDate.issued();
    var userAuthentication = new UserAuthentication(token, expiration.toString(),
        issued.toString(), issuer);
    applicationEventPublisher.publishEvent(new UserAuthenticationEvent(email, issued, expiration));
    return userAuthentication;
  }

  public UserAuthentication generateAuthenticationToken(String email) {
    return generateUserAuthentication(email, getDefaultIssuedAndExpiration());
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

  private IssuedAndExpiration getDefaultIssuedAndExpiration() {
    var now = clock.instant();
    var expiration = now.plus(Duration.ofDays(jwtExpiration));
    var currentDate = Date.from(now);
    var expirationDate = Date.from(expiration);
    return new IssuedAndExpiration(currentDate, expirationDate);

  }

  private record IssuedAndExpiration(Date issued, Date expiration) {
  }

}
