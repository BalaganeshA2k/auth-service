package com.zano.authenticationservice.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.zano.authenticationservice.user.authority.UserAuthority;

@DataJpaTest
public class UserDetailRepositoryTest {
    @Autowired
    private UserDetailRepository userDetailRepository;

    @Test
    void assertSetUp() {
        Assertions.assertThat(userDetailRepository).isNotNull();
    }

    private String userEmail = "balaganesh.a2k@gmail.com";
    private String username = "bala";
    private String password = "pass";
    private Set<UserAuthority> authorities = Set.of(UserAuthority.builder().authority("user").build());
    UserDetail userDetail = UserDetail.builder()
            .username(username)
            .userEmail(userEmail)
            .password(password)
            .authorities(authorities)
            .build();

    @Test
    public void existsByUsernameShouldReturnTrueIfUserExists() {
        userDetailRepository.save(userDetail);

        assertTrue(() -> userDetailRepository.existsByUserEmail(userEmail));
    }

    @Test
    public void existsByUsernameShouldReturnFalseIfUserDoesNotExists() {
        assertFalse(() -> userDetailRepository.existsByUserEmail(userEmail));
    }

    @Test
    public void findOneByUserEmailShouldReturnUserIfExists() {
        userDetailRepository.save(userDetail);
        var actual = userDetailRepository.findByUserEmail(userEmail).get();
        assertEquals(userDetail, actual, "UserRepository.findOneByUserEmail is not returning saved user value");
    }

    @Test
    public void findOneByUserEmailShouldNotReturnUserIfDooesNotExists() {
        var isUserPresent = userDetailRepository.findByUserEmail(userEmail).isPresent();
        assertFalse(isUserPresent);
    }

    @Test
    public void findAuthoritiesByUserEmailReturnsAuthorities() {
        userDetailRepository.save(userDetail);

        var authorities = userDetailRepository.findAuthoritiesByUserEmail(userEmail);
        Assertions.assertThat(authorities).containsAll(this.authorities);
    }

}
