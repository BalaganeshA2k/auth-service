package com.zano.authenticationservice.user.registration;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSignInService {
    private final ApplicationEventPublisher applicationEventPublisher;

    public String signInNewUser(UserSignInRequest userSignInRequest) {
        var email = userSignInRequest.email();
        applicationEventPublisher.publishEvent(userSignInRequest);
        return "Otp will be sent to:" + email;
    }
}
