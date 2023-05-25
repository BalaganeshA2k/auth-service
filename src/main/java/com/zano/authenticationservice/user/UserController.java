package com.zano.authenticationservice.user;

import static org.springframework.http.HttpStatus.PROCESSING;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
  private final UserDetailService userDetailService;

  @PostMapping
  @ResponseStatus(PROCESSING)
  public void userSignIn(
      @RequestBody @Validated UserSignInRequest userSignInRequest) {
    userDetailService.signInUser(userSignInRequest);
  }

}
