package com.solidcode.security.validators;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({PARAMETER, METHOD, FIELD, LOCAL_VARIABLE})
@Retention(RUNTIME)
@Constraint(validatedBy = ProtocolTypeValidator.class)
@Documented
public @interface ValidProtocolType {

  String message() default "Invalid protocol type";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
