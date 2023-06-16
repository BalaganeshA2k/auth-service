package com.zano.authenticationservice.user.authority;

import static com.zano.authenticationservice.ApplicationRoles.ROLE_USER;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAuthorityService {
    private final UserAuthorityRepository authorityRepository;

    public CreatedUserAuthority createNewUserAuthority(UserAuthorityRequest authorityRequest) {
        var authority = authorityRequest.toAuthority();
        var authorityInDb = authorityRepository.save(authority);
        var name = authorityInDb.getAuthority();
        return new CreatedUserAuthority(name);
    }

    public Set<UserAuthority> getDefaultUserAuthority() {
        return authorityRepository
                .findById(ROLE_USER.name()).map(Stream::of)
                .orElseThrow()
                .collect(Collectors.toSet());

    }

    public UserAuthority getUserAuthorityByName(String name) {
        return authorityRepository.findById(name).orElseThrow();

    }

}
