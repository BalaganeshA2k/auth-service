package com.zano.authenticationservice.user;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailService {
  private final ApplicationEventPublisher applicationEventPublisher;

  public void signInUser(UserSignInRequest userSignInRequest) {
    applicationEventPublisher.publishEvent(userSignInRequest);
  }

}
