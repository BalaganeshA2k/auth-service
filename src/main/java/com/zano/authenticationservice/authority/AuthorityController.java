package com.zano.authenticationservice.authority;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthorityController {
    private final AuthorityService authorityService;

    @PostMapping(value = "/api/v1/authority")
    @ResponseStatus(CREATED)
    public void postNewAuthority(@RequestBody AuthorityRequest authorityRequest) {
        authorityService.saveNewAuthority(authorityRequest.toUserAuthority());
    }

}
