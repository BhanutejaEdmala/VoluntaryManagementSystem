package com.example.Vms.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class CustomValidator implements ConstraintValidator<MyValid, Set<String>> {
    @Override
    public boolean isValid(Set value, ConstraintValidatorContext context) {
       if(value.isEmpty())
           return false;
       if(value.stream().anyMatch(i-> Pattern.compile("\\d").matcher(i.toString()).find()))
           return false;
       return true;

    }
}
