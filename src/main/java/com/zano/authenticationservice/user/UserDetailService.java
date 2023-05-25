package com.zano.authenticationservice.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailService {
  private final UserDetailRepository userDetailRepository;

  public UserSignInStatus signInUser(UserSignInRequest userSignInRequest) {
    var userDetailsExists = userDetailRepository.existsByUsername(userSignInRequest.getUsername());
    if (userDetailsExists)
      return UserSignInStatus.USER_ALREADY_SIGNED_IN;
    var userDetail = userSignInRequest.toUserDetail();
    userDetailRepository.save(userDetail);
    return UserSignInStatus.SIGN_IN_SUCCESSFULL;
  }
}
