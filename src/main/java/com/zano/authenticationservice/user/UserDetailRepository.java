package com.zano.authenticationservice.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
  Optional<UserDetail> findOneByUserEmail(String userEmail);

  boolean existsByUserEmail(String userEmail);

  Optional<Long> findIdByUserEmail(String userEmail);
}
