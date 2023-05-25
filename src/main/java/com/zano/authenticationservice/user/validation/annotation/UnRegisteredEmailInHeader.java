package com.zano.authenticationservice.user.validation.annotation;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.RECORD_COMPONENT;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.zano.authenticationservice.user.validation.UnRegisteredEmailInHeaderValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ FIELD, CONSTRUCTOR, RECORD_COMPONENT, PARAMETER, METHOD })
@Retention(RUNTIME)
@Constraint(validatedBy = UnRegisteredEmailInHeaderValidator.class)
public @interface UnRegisteredEmailInHeader {

    String message() default "email already registered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
