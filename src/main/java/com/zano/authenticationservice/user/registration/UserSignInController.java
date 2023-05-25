package com.zano.authenticationservice.user.registration;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user/registration")
@RequiredArgsConstructor
public class UserSignInController {
    private final UserSignInService userSignInService;

    @PostMapping
    public String signInUser(@Validated @RequestBody UserSignInRequest userSignInRequest) {
        return userSignInService.signInNewUser(userSignInRequest);
    }
}
