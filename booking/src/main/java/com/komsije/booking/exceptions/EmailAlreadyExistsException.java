package com.komsije.booking.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }}