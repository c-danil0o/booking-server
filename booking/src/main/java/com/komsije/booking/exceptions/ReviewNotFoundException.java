package com.komsije.booking.exceptions;

public class ReviewNotFoundException extends  RuntimeException{

    public ReviewNotFoundException(String msg){
        super(msg);
    }
}
