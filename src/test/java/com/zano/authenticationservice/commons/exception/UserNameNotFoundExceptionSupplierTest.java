package com.zano.authenticationservice.commons.exception;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserNameNotFoundExceptionSupplierTest {
    @Test
    void getShouldReturnUserNameNotFoundException() {
        UserNameNotFoundExceptionSupplier supplier = new UserNameNotFoundExceptionSupplier();
        Assertions.assertThat(supplier.get())
                .isExactlyInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User Not Registered");
    }

}
