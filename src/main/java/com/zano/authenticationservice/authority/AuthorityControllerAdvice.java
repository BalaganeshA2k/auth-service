package com.zano.authenticationservice.authority;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zano.authenticationservice.ApplicationErrorResponse;
import com.zano.authenticationservice.authority.exception.AuthorityAlreadyExistsException;

@RestControllerAdvice
public class AuthorityControllerAdvice {
    @ExceptionHandler(AuthorityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApplicationErrorResponse handleAuthorityAlreadyExistsException(AuthorityAlreadyExistsException ex) {
        return new ApplicationErrorResponse(ex.getMessage());
    }
}
