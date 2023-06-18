package com.zano.authenticationservice.authority.exception;

public class AuthorityAlreadyExistsException extends RuntimeException {
    private static final String MESSAGE = "AUTHORITY ALREADY EXIST";

    public AuthorityAlreadyExistsException() {
        super(MESSAGE);
    }

}
