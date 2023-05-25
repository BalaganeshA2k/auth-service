package com.zano.authenticationservice.user;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @PostMapping(value = "/api/v1/user")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void saveNewUser(@RequestBody NewUser user, @RequestHeader(AUTHORIZATION) String authorisationHeaderValue) {
        userDetailService.saveNewUser(user, authorisationHeaderValue);
    }

    public record NewUser(String username, String password) {
    }

}
