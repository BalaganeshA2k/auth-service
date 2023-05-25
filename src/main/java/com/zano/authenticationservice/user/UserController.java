package com.zano.authenticationservice.user;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.ACCEPTED;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zano.authenticationservice.user.dto.NewUser;
import com.zano.authenticationservice.user.validation.annotation.UnRegisteredEmailInHeader;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@Validated
@RequiredArgsConstructor
public class UserController {
    private final UserDetailService userDetailService;

    @GetMapping(value = "/api/v1/users/{username}/taken")
    public ResponseEntity<Boolean> isUserNameTaken(@PathVariable String username) {
        var existByUserName = userDetailService.isUserNameTaken(username);
        return ResponseEntity.ok().body(existByUserName);
    }

    @PostMapping(value = "/api/v1/user")
    @ResponseStatus(ACCEPTED)
    public void saveNewUser(
            @Valid @RequestBody NewUser user,
            @Valid @UnRegisteredEmailInHeader @RequestHeader("X-" + AUTHORIZATION) String authorisationHeaderValue) {
        userDetailService.saveNewUser(user, authorisationHeaderValue);
    }

}
