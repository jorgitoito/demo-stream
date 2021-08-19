package com.jmr.stream.demostream.model.validator;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class DniValidator implements ConstraintValidator<Dni, String> {


    private final Pattern mask = Pattern.compile("[0-9]{8,8}[A-Z]");

    @Override
    public void initialize(Dni constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        log.info("isValid: dni [{}] ", value);
        final Matcher matcher = mask.matcher(value);

        if (!matcher.matches()) {
            return false;
        }

        final String dni = value.substring(0, 8);
        final String control = value.substring(8, 9);
        final String letters = "TRWAGMYFPDXBNJZSQVHLCKE";
        final int position = Integer.parseInt(dni) % 23;

        final String controlCalculated = letters.substring(position, position + 1);

        return control.equalsIgnoreCase(controlCalculated);
    }


}
