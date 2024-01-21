package com.komsije.booking.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class AccommodationTypeConstraintValidator implements ConstraintValidator<AccommodationTypeConstraint, String> {
    @Override
    public void initialize(AccommodationTypeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        List<String> types = new ArrayList<>();
        types.add("Room");
        types.add("Apartment");
        types.add("Hotel");
        return types.contains(value);
    }

}
