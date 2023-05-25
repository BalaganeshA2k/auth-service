package com.zano.authenticationservice.authority;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority,Long>{
    Set<Authority> findByName(String name);
}
