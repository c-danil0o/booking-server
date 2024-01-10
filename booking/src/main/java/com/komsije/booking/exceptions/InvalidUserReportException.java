package com.komsije.booking.exceptions;

public class InvalidUserReportException extends RuntimeException{
    public InvalidUserReportException(String msg){
        super(msg);
    }
}
