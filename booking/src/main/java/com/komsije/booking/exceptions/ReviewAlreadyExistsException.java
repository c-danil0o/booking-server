package com.komsije.booking.exceptions;

public class ReviewAlreadyExistsException extends RuntimeException{
    public ReviewAlreadyExistsException(String msg){
        super(msg);
    }
}
