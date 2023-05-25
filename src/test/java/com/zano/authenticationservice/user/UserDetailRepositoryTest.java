package com.zano.authenticationservice.user;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserDetailRepositoryTest {
    @Autowired
    private UserDetailRepository userDetailRepository;

    @Test
    public void existsByUsernameShouldReturnTrueIfUserExists() {
        UserDetail userDetail = UserDetail.builder()
                .userEmail("balaganesh.a2k@gmail.com")
                .password("pass")
                .authorities(Set.of())
                .build();
        userDetailRepository.save(userDetail);

        assertTrue(() -> userDetailRepository.existsByUserEmail("balaganesh.a2k@gmail.com"));
    }

    @Test
    public void existsByUsernameShouldReturnFalseIfUserDoesNotExists() {
        assertFalse(() -> userDetailRepository.existsByUserEmail("balaganesh.a2k@gmail.com"));
    }
}
