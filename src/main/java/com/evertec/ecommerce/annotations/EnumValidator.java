package com.evertec.ecommerce.annotations;


import com.evertec.ecommerce.utils.EnumValidatorImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidatorImpl.class)
public @interface EnumValidator {

    Class<? extends Enum<?>> enumClass();

    String message() default "Invalid value. Must be one of the specified Enum values.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
