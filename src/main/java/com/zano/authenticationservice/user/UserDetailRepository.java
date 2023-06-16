package com.zano.authenticationservice.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, String> {
  Optional<UserDetail> findByUserEmail(String userEmail);

  boolean existsByUserEmail(String userEmail);

}
