package com.komsije.booking.exceptions;

public class ReservationAlreadyExistsException extends  RuntimeException{
    public ReservationAlreadyExistsException(String msg){
        super(msg);
    }
}
