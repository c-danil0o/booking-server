package com.komsije.booking.exceptions;

public class ElementNotFoundException extends Exception{
    public ElementNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
