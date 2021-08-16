package com.jmr.stream.demostream.model.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DniValidator.class)
public @interface Dni {
    String message() default "Dni erroneo";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
