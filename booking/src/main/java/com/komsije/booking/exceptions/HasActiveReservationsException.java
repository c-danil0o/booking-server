package com.komsije.booking.exceptions;

public class HasActiveReservationsException extends RuntimeException {
    public HasActiveReservationsException(String errorMessage){
        super(errorMessage);
    }
}
