package com.zano.authenticationservice.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserDetailService userDetailService;

    @GetMapping(value = "/api/v1/users/{username}/taken")
    public ResponseEntity<Boolean> isUserNameTaken(@PathVariable String username) {
        var existByUserName = userDetailService.isUserNameTaken(username);
        return ResponseEntity.ok().body(existByUserName);
    }

}
