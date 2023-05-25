package com.zano.authenticationservice.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {
  
  @PostMapping("/user")
  public String userSignIn(@RequestBody UserSignInRequest userSignInRequest){
    userSignInRequest.getUsername();
    userSignInRequest.getPassword();
    return userSignInRequest.getUsername()+" accepted";
  }

}
