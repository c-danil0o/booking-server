package com.komsije.booking.exceptions;

public class ReviewAlreadyReportedException extends RuntimeException{
    public ReviewAlreadyReportedException(String msg){
        super(msg);
    }
}
