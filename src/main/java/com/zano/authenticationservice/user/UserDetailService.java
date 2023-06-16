package com.zano.authenticationservice.user;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zano.authenticationservice.commons.exception.UserNameNotFoundExceptionSupplier;
import com.zano.authenticationservice.jwt.JwtDetailsExtractor;
import com.zano.authenticationservice.user.authority.UserAuthority;
import com.zano.authenticationservice.user.authority.UserAuthorityService;
import com.zano.authenticationservice.user.dto.NewUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailService {
  private final UserDetailRepository userDetailRepository;
  private final UserAuthorityService authorityService;
  private final UserNameNotFoundExceptionSupplier userNameNotFoundExceptionSupplier;
  private final JwtDetailsExtractor jwtDetailsExtractor;
  private final PasswordEncoder passwordEncoder;

  public UserDetail saveNewUser(NewUser newUser, String authorisationHeaderValue) {
    var email = jwtDetailsExtractor.extractSubjectFromBearerToken(authorisationHeaderValue);
    String encodedPassword = passwordEncoder.encode(newUser.password());
    Set<UserAuthority> defaultUserAuthority = authorityService.getDefaultUserAuthority();

    var user = UserDetail.builder()
        .username(newUser.username())
        .userEmail(email)
        .password(encodedPassword)
        .authorities(defaultUserAuthority)
        .build();
    return userDetailRepository.save(user);
  }

  public UserDetail getUserDetailByEmail(String email) {
    return userDetailRepository.findByUserEmail(email)
        .orElseThrow(userNameNotFoundExceptionSupplier);
  }

  public Boolean getAvailabilityOfUserName(String username) {
    return !userDetailRepository.existsById(username);
  }

  public boolean isEmailRegistered(String email) {
    return userDetailRepository.existsByUserEmail(email);
  }

}
