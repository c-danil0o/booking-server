package com.komsije.booking.exceptions;

public class InvalidTimeSlotException extends RuntimeException{
    public InvalidTimeSlotException(String errorMessage){
        super(errorMessage);
    }
}
