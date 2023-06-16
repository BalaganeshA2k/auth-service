package com.zano.authenticationservice.user.authority;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthorityServiceTest {
    @Mock
    private UserAuthorityRepository authorityRepository;
    @InjectMocks
    private UserAuthorityService authorityService;
    private String mockAuthorityName = "user";
    private UserAuthority mockAuthority = UserAuthority.builder()
            .authority(mockAuthorityName)
            .build();
    private UserAuthorityRequest mockAuthorityRequest = new UserAuthorityRequest(mockAuthorityName);
    private CreatedUserAuthority mockCreatedAuthority = new CreatedUserAuthority(mockAuthorityName);

    @Test
    void createNewAuthorityShouldReturnCreatedAuthority() {
        when(authorityRepository.save(any(UserAuthority.class)))
            .thenReturn(mockAuthority);
        Assertions.assertThat(authorityService.createNewUserAuthority(mockAuthorityRequest))
            .isNotNull()
            .isEqualTo(mockCreatedAuthority);
    }

    @Test
    void getDefaultUserAuthorityShouldReturnDefaultAuthority() {
        when(authorityRepository.findById(any(String.class)))
        .thenReturn(Optional.of(mockAuthority));
        Assertions.assertThat(authorityService.getDefaultUserAuthority())
            .isNotEmpty()
            .containsExactly(mockAuthority);
    }
}
