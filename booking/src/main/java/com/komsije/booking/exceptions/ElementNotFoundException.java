package com.komsije.booking.exceptions;

public class ElementNotFoundException extends RuntimeException{
    public ElementNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
