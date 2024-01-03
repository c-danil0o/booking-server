package com.komsije.booking.exceptions;

public class CancellationDeadlineExpiredException extends RuntimeException{
    public CancellationDeadlineExpiredException(String message){
        super(message);
    }
}
