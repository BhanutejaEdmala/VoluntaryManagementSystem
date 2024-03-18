package com.example.Vms.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TimingsValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Timings {
    public String message() default "timings should be given in format hh:mm to hh:mm";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
