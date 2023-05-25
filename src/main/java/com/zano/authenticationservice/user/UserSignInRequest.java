package com.zano.authenticationservice.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSignInRequest {
  private String username;
  private String password;
  public UserDetail toUserDetail(){
    return UserDetail.builder()
      .username(username)
      .password(password)
      .build();
  }
}
