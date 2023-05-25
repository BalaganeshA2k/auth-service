package com.zano.authenticationservice.user.detail;

import org.springframework.stereotype.Service;

import com.zano.authenticationservice.authority.AuthorityService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailService {
  private UserDetailRepository userDetailRepository;
  private AuthorityService authorityService;

  public void saveNewUser(String email, String password) {
    var user = UserDetail.builder()
        .userEmail(email)
        .password(password)
        .authorities(authorityService.getDefaultUserAuthority())
        .build();
    userDetailRepository.save(user);
  }

}
