package com.jmr.stream.demostream.model.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
public @interface Phone {
    /**
     * Mensaje de validación fallida
     */
    String message() default "Ingrese el número de teléfono correcto";

    /**
     * Verificación de grupo
     */
    Class<?>[] groups() default {};


    Class<? extends Payload>[] payload() default {};
}
