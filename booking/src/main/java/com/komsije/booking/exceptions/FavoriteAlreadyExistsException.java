package com.komsije.booking.exceptions;

public class FavoriteAlreadyExistsException extends RuntimeException{
    public FavoriteAlreadyExistsException(String msg){
        super(msg);
    }
}
