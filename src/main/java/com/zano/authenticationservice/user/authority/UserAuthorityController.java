package com.zano.authenticationservice.user.authority;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/authority")
@RequiredArgsConstructor
public class UserAuthorityController {
    private final UserAuthorityService authorityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedUserAuthority createNewAuthority(@RequestBody UserAuthorityRequest authorityRequest) {
        return authorityService.createNewUserAuthority(authorityRequest);
    }

}
