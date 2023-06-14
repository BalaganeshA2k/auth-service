package com.zano.authenticationservice.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserDetailRepositoryTest {
    @Autowired
    private UserDetailRepository userDetailRepository;

    @Test
    void assertSetUp() {
        Assertions.assertThat(userDetailRepository).isNotNull();
    }

    UserDetail userDetail = UserDetail.builder()
            .username("bala")
            .userEmail("balaganesh.a2k@gmail.com")
            .password("pass")
            .authorities(Set.of())
            .build();

    @Test
    public void existsByUsernameShouldReturnTrueIfUserExists() {
        userDetailRepository.save(userDetail);

        assertTrue(() -> userDetailRepository.existsByUserEmail("balaganesh.a2k@gmail.com"));
    }

    @Test
    public void existsByUsernameShouldReturnFalseIfUserDoesNotExists() {
        assertFalse(() -> userDetailRepository.existsByUserEmail("balaganesh.a2k@gmail.com"));
    }

    @Test
    public void findOneByUserEmailShouldReturnUserIfExists() {
        userDetailRepository.save(userDetail);
        var actual = userDetailRepository.findOneByUserEmail("balaganesh.a2k@gmail.com").get();
        assertEquals(userDetail, actual, "UserRepository.findOneByUserEmail is not returning saved user value");
    }

    @Test
    public void findOneByUserEmailShouldNotReturnUserIfDooesNotExists() {
        var isUserPresent = userDetailRepository.findOneByUserEmail("balaganesh.a2k@gmail.com").isPresent();
        assertFalse(isUserPresent);
    }

}
