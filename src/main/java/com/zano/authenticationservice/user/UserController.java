package com.zano.authenticationservice.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
  private final UserDetailService userDetailService;

  @PostMapping
  public UserSignInStatus userSignIn(@RequestBody UserSignInRequest userSignInRequest){
    return userDetailService
    .signInUser(userSignInRequest);
  }


}
