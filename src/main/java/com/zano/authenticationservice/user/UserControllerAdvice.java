package com.zano.authenticationservice.user;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerAdvice {
  @ExceptionHandler(DuplicateUserSignInRequestException.class)
  public ResponseEntity duplicateUserSignInRequestExceptionHandler(DuplicateUserSignInRequestException duplicateUserSignInRequestException){
    var response = Map.of("Status", UserSignInStatus.SIGN_IN_FAILED,
       "message", "User already exists");
    return ResponseEntity.badRequest()
      .body(response);
  }
}
