package com.jmr.stream.demostream.model.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class PhoneValidator implements ConstraintValidator<Phone, String> {
    @Override
    public void initialize(Phone constraintAnnotation) {

    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        if (!StringUtils.isEmpty(phone)) {
            // Obtener la información de solicitud predeterminada
            String defaultConstraintMessageTemplate = constraintValidatorContext.getDefaultConstraintMessageTemplate();
            log.info("default message [{}] ", defaultConstraintMessageTemplate);
            // Deshabilitar el mensaje de aviso predeterminado
            constraintValidatorContext.disableDefaultConstraintViolation();
            // Establecer aviso
            constraintValidatorContext.buildConstraintViolationWithTemplate("Error de formato de número de teléfono móvil").addConstraintViolation();

            String regex = "^1(3|4|5|7|8)\\d{9}$";
            return phone.matches(regex);
        }
        return true;
    }
}
