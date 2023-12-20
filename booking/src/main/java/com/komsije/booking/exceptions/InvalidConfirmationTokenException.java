package com.komsije.booking.exceptions;

public class InvalidConfirmationTokenException extends RuntimeException{
    public InvalidConfirmationTokenException(String errorMessage) {
        super(errorMessage);
    }
}
