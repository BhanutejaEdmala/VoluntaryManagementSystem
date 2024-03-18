package com.example.Vms.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimingsValidator implements ConstraintValidator<Timings,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try{
            String[] timings =value.split("to");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
            LocalTime startTime=  LocalTime.parse(timings[0].strip(),formatter);
            LocalTime EndTime=  LocalTime.parse(timings[1].strip(),formatter);
            return true;
        }
        catch (Exception e){
            return false;}
    }
}
