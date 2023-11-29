package com.komsije.booking.exceptions;

public class HasActiveReservationsException extends Exception {
    public HasActiveReservationsException(String errorMessage){
        super(errorMessage);
    }
}
