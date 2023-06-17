package com.zano.authenticationservice.user;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zano.authenticationservice.user.authority.UserAuthority;

public interface UserDetailRepository extends JpaRepository<UserDetail, String> {
  Optional<UserDetail> findByUserEmail(String userEmail);

  boolean existsByUserEmail(String userEmail);

  @Query("SELECT ua FROM UserDetail ud JOIN ud.authorities ua WHERE ud.userEmail = :userEmail")
  Set<UserAuthority> findAuthoritiesByUserEmail(String userEmail);

}
