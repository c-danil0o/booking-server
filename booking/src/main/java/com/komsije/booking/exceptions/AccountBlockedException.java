package com.komsije.booking.exceptions;

public class AccountBlockedException extends RuntimeException{
    public AccountBlockedException(String errorMessage){
        super(errorMessage);
    }
}
