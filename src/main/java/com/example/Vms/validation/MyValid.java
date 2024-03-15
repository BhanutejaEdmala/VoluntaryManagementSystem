package com.example.Vms.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CustomValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyValid {
    public String message() default " skills shouldn't be empty and they shouldn't consist of numbers";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
