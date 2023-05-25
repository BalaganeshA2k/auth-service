package com.zano.authenticationservice.user.validation.annotation;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.RECORD_COMPONENT;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.zano.authenticationservice.user.validation.UnRegisteredUserNameValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ FIELD, PARAMETER, CONSTRUCTOR, RECORD_COMPONENT })
@Retention(RUNTIME)
@Constraint(validatedBy = UnRegisteredUserNameValidator.class)
public @interface UnRegisteredUserName {
    String message() default "username taken";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
