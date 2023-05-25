package com.zano.authenticationservice.authority;

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
public class AuthorityController {
    private final AuthorityService authorityService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedAuthority createNewAuthority(@RequestBody AuthorityRequest authorityRequest) {
        return authorityService.createNewAuthority(authorityRequest);
    }
    

}
