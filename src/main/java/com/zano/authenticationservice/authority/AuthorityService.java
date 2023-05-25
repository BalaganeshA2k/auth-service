package com.zano.authenticationservice.authority;

import static com.zano.authenticationservice.ApplicationRoles.ROLE_USER;

import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorityService {
    private final AuthorityRepository authorityRepository;
    public CreatedAuthority createNewAuthority(AuthorityRequest authorityRequest) {
        var authority = authorityRequest.toAuthority();
        var authorityInDb = authorityRepository.save(authority);
        var id = authorityInDb.getId();
        var name = authorityInDb.getName();
        return new CreatedAuthority(id,name);
    }
    public Set<Authority> getDefaultUserAuthority() {
        return authorityRepository.findByName(ROLE_USER.name());
    }

}
