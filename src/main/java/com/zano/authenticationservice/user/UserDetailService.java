package com.zano.authenticationservice.user;

import org.springframework.stereotype.Service;

import com.zano.authenticationservice.authority.AuthorityService;
import com.zano.authenticationservice.commons.exception.UserNameNotFoundExceptionSupplier;
import com.zano.authenticationservice.jwt.JwtService;
import com.zano.authenticationservice.user.dto.NewUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailService {
  private final UserDetailRepository userDetailRepository;
  private final AuthorityService authorityService;
  private final UserNameNotFoundExceptionSupplier userNameNotFoundExceptionSupplier;
  private final JwtService jwtService;

  public void saveNewUser(NewUser newUser, String authorisationHeaderValue) {
    var email = jwtService.extractSubjectFromBearerToken(authorisationHeaderValue);
    var user = UserDetail.builder()
        .username(newUser.username())
        .userEmail(email)
        .password(newUser.password())
        .authorities(authorityService.getDefaultUserAuthority())
        .build();
    userDetailRepository.save(user);
  }

  public UserDetail getUserDetailByEmail(String email) {
    return userDetailRepository.findOneByUserEmail(email)
        .orElseThrow(userNameNotFoundExceptionSupplier);
  }

  public Boolean isUserNameTaken(String username) {
    return userDetailRepository.existsById(username);
  }

  public boolean isEmailRegistered(String email) {
    return userDetailRepository.existsByUserEmail(email);
  }

}
