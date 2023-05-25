package com.zano.authenticationservice.user.detail;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {

  boolean existsByUserEmail(String userEmail);
}
