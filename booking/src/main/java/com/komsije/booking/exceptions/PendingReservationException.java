package com.komsije.booking.exceptions;

public class PendingReservationException extends RuntimeException{
    public PendingReservationException(String message){
        super(message);
    }
}
