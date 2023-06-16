package com.zano.authenticationservice.user.email.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = UserEmailHasNoActiveOtpValidator.class)
public @interface UserEmailHasNoActiveOtp {
    String message() default "Previously sent otp has not been expired";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
