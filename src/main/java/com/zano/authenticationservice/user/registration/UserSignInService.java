package com.zano.authenticationservice.user.registration;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.zano.authenticationservice.security.RandomEncodedPasswordGenerator;
import com.zano.authenticationservice.user.detail.UserDetailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSignInService {
    private final RandomEncodedPasswordGenerator passwordGenerator;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserDetailService userDetailService;

    public String signInNewUser(UserSignInRequest userSignInRequest) {
        String email = userSignInRequest.email();
        var password = passwordGenerator.generate();
        var userEmailVerificationEvent = new UserEmailVerificationEvent(email, password);
        applicationEventPublisher.publishEvent(userEmailVerificationEvent);
        userDetailService.saveNewUser(email, password);
        return "Password will be sent to : " + email;
    }
}
