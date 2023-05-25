package com.zano.authenticationservice.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.zano.authenticationservice.user.detail.UserDetail;
import com.zano.authenticationservice.user.detail.UserDetailRepository;

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

    @Test
    public void findOneByUserEmailShouldReturnUserIfExists() {
        var expected = UserDetail.builder()
                .userEmail("balaganesh.a2k@gmail.com")
                .password("pass")
                .authorities(Set.of())
                .build();
        userDetailRepository.save(expected);
        var actual = userDetailRepository.findOneByUserEmail("balaganesh.a2k@gmail.com").get();
        assertEquals(expected, actual, "UserRepository.findOneByUserEmail is not returning saved user value");
    }

    @Test
    public void findOneByUserEmailShouldNotReturnUserIfDooesNotExists() {
        var isUserPresent = userDetailRepository.findOneByUserEmail("balaganesh.a2k@gmail.com").isPresent();
        assertFalse(isUserPresent);
    }

}
