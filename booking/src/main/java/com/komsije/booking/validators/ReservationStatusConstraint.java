package com.komsije.booking.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= ReservationStatusConstraintValidator.class)
public @interface ReservationStatusConstraint {
    String message() default "Not valid reservation status!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
