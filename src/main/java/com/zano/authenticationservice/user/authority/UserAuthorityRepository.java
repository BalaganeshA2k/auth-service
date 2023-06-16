package com.zano.authenticationservice.user.authority;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, String> {

}
