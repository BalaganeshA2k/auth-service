package com.zano.authenticationservice.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailService {
  private final UserDetailRepository userDetailRepository;
  private final UserSignInRequestToDefaultUserDetail userSignInRequestToDefaultUserDetail;
  public void signInUser(UserSignInRequest userSignInRequest) {
    boolean userDetailsExists = 
      userDetailRepository.existsByUsername(userSignInRequest.username());
    if (userDetailsExists)
      throw new UserAlreadyExistsException();
    var userDetail = userSignInRequestToDefaultUserDetail.apply(userSignInRequest);
    userDetailRepository.save(userDetail);
  }
}
