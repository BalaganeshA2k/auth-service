package com.zano.authenticationservice.authentication;

public record UserAuthentication(String token, String expiration, String issuedAt, String issuedBy) {

}
