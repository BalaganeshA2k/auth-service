package com.zano.authenticationservice.jwt;

import java.util.Date;
import java.util.function.Function;
import org.springframework.stereotype.Service;

import com.zano.authenticationservice.jwt.exception.AuthorisationHeaderHasNoBearerException;
import com.zano.authenticationservice.jwt.exception.InvalidAuthorisationTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtDetailsExtractor {
  private final JwtSigningKeySupplier signKeySupplier;

  public String extractTokenFromAuthorisationHeader(String tokenWithBearer) {
    if (!tokenWithBearer.startsWith("Bearer "))
      throw new InvalidAuthorisationTokenException();
    return tokenWithBearer.substring(7);
  }

  public String extractSubjectFromBearerToken(String headerValue) {
    if (!headerValue.startsWith("Bearer "))
      throw new AuthorisationHeaderHasNoBearerException();
    return extractSubject(headerValue.substring(7));
  }

  public String extractSubject(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(signKeySupplier.get())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

}
