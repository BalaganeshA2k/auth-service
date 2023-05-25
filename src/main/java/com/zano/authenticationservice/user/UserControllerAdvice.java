package com.zano.authenticationservice.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.zano.authenticationservice.ApplicationErrorResponse;

@RestControllerAdvice
public class UserControllerAdvice {
    private enum ResponseMessages{
        USER_ALREADY_EXISTS
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ApplicationErrorResponse duplicateUserInsertionHandler(UserAlreadyExistsException exception){
        return new ApplicationErrorResponse(ResponseMessages.USER_ALREADY_EXISTS.name());
    }
}
