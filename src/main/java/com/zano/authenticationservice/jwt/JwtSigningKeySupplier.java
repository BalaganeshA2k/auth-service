package com.zano.authenticationservice.jwt;

import java.security.Key;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtSigningKeySupplier implements Supplier<Key> {
  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  @Override
  public Key get() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
