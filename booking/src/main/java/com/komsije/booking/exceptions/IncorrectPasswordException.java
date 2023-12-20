package com.komsije.booking.exceptions;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String errorMessage) {
        super(errorMessage);
    }
}
