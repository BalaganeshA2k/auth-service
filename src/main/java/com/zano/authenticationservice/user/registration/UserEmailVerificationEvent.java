package com.zano.authenticationservice.user.registration;

public record UserEmailVerificationEvent(String email, String password) {

}
