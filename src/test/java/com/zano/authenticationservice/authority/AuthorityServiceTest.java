package com.zano.authenticationservice.authority;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthorityServiceTest {
    @Mock
    private AuthorityRepository authorityRepository;
    @InjectMocks
    private AuthorityService authorityService;
    private String mockAuthorityName = "user";
    private Authority mockAuthority = Authority.builder()
            .name(mockAuthorityName)
            .id(1L)
            .build();
    private AuthorityRequest mockAuthorityRequest = new AuthorityRequest(mockAuthorityName);
    private CreatedAuthority mockCreatedAuthority = new CreatedAuthority(1L, mockAuthorityName);

    @Test
    void createNewAuthorityShouldReturnCreatedAuthority() {
        when(authorityRepository.save(any(Authority.class)))
            .thenReturn(mockAuthority);
        Assertions.assertThat(authorityService.createNewAuthority(mockAuthorityRequest))
            .isNotNull()
            .isEqualTo(mockCreatedAuthority);
    }

    @Test
    void getDefaultUserAuthorityShouldReturnDefaultAuthority() {
        when(authorityRepository.findByName(any(String.class)))
        .thenReturn(Set.of(mockAuthority));
        Assertions.assertThat(authorityService.getDefaultUserAuthority())
            .isNotEmpty()
            .containsExactly(mockAuthority);
    }
}
