package com.zano.authenticationservice.user.authority;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zano.authenticationservice.jwt.JwtDetailsExtractor;
import com.zano.authenticationservice.user.dto.UserAuthentication;

import lombok.RequiredArgsConstructor;

//NOT SECURED YET
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserAuthorityController {
    private final UserAuthorityService authorityService;
    private final JwtDetailsExtractor detailsExtractor;

    @PostMapping(value = "/authority")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedUserAuthority createNewAuthority(@RequestBody UserAuthorityRequest authorityRequest) {
        return authorityService.createNewUserAuthority(authorityRequest);
    }

    @GetMapping(value = "/authorities")
    public UserAuthorities getUserAuthorities(@RequestBody UserAuthentication userAuthentication) {
        var email = detailsExtractor.extractSubject(userAuthentication.token());
        return authorityService.getUserAuthorities(email);
    }

}
