package com.zano.authenticationservice.user.detail;

import org.springframework.stereotype.Service;

import com.zano.authenticationservice.authority.AuthorityService;
import com.zano.authenticationservice.commons.exception.UserNameNotFoundExceptionSupplier;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailService {
  private final UserDetailRepository userDetailRepository;
  private final AuthorityService authorityService;
  private final UserNameNotFoundExceptionSupplier userNameNotFoundExceptionSupplier;

  public void saveNewUser(String email, String password) {
    var user = UserDetail.builder()
        .userEmail(email)
        .password(password)
        .authorities(authorityService.getDefaultUserAuthority())
        .build();
    userDetailRepository.save(user);
  }

  public UserDetail getUserDetailByEmail(String email) {
    return userDetailRepository.findOneByUserEmail(email)
        .orElseThrow(userNameNotFoundExceptionSupplier);
  }

}
