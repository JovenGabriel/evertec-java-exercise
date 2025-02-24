package com.evertec.ecommerce.utils;


import com.evertec.ecommerce.annotations.EnumValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, Enum<?>> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // Puedes ajustar si es necesario que acepte valores nulos.
        }
        for (Enum<?> enumValue : enumClass.getEnumConstants()) {
            if (enumValue.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
