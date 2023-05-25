package com.zano.authenticationservice.user;

public record UserAuthentication(String token, String expiration, String issuedAt, String issuedBy) {

}
