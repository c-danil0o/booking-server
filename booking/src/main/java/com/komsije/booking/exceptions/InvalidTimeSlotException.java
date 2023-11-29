package com.komsije.booking.exceptions;

public class InvalidTimeSlotException extends Exception{
    public InvalidTimeSlotException(String errorMessage){
        super(errorMessage);
    }
}
