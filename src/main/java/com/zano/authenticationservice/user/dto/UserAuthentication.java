package com.zano.authenticationservice.user.dto;

public record UserAuthentication(String token, String expiration, String issuedAt, String issuedBy) {

}
