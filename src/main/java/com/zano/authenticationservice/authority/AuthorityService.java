package com.zano.authenticationservice.authority;

import org.springframework.stereotype.Service;

import com.zano.authenticationservice.authority.exception.AuthorityAlreadyExistsException;
import com.zano.authenticationservice.user.authority.UserAuthority;
import com.zano.authenticationservice.user.authority.UserAuthorityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorityService {
    private final UserAuthorityRepository userAuthorityRepository;

    public void saveNewAuthority(UserAuthority userAuthority) {
        if (userAuthorityRepository.existsByAuthority(userAuthority.getAuthority()))
            throw new AuthorityAlreadyExistsException();

        userAuthorityRepository.save(userAuthority);
    }

}
