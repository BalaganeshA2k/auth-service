package com.zano.authenticationservice.commons.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserNameNotFoundExceptionSupplier implements ExceptionSupplier<UsernameNotFoundException> {

    @Override
    public UsernameNotFoundException get() {
        return new UsernameNotFoundException("User Not Registered");
    }

}
