package com.komsije.booking.validators;

import com.komsije.booking.model.ReservationStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ReservationStatusConstraintValidator implements ConstraintValidator<ReservationStatusConstraint, String> {
    @Override
    public void initialize(ReservationStatusConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.asList(ReservationStatus.Pending.toString(), ReservationStatus.Approved.toString(), ReservationStatus.Denied.toString(), ReservationStatus.Active.toString(), ReservationStatus.Done.toString(), ReservationStatus.Cancelled.toString()).contains(value);
    }
}
