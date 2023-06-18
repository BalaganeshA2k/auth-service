package com.zano.authenticationservice.user.authority;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, String> {

    Optional<UserAuthority> findByAuthority(String name);

    boolean existsByAuthority(String authority);

}
