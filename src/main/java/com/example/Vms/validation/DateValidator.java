package com.example.Vms.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidator implements ConstraintValidator<Date,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        date.setLenient(false); // Set lenient to false to enforce strict date parsing
        try {
            // Attempt to parse the date string
            date.parse(value);
            return true; // If parsing is successful, the date is valid
        } catch (Exception e) {
            return false; // If an exception is thrown, the date is invalid
        }
    }
}
