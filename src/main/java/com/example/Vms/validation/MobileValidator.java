package com.example.Vms.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.regex.Pattern;

public class MobileValidator implements ConstraintValidator<MValid,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(!(value.startsWith("6")||value.startsWith("7")||value.startsWith("8")||value.startsWith("9")))
             return false;
        char[] sa = value.toCharArray();
        for(char i :sa){
            if(!(Character.isDigit(i)))
                return false;
        }
        return true;
    }
}
