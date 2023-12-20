package com.komsije.booking.exceptions;

public class UploadFileException extends RuntimeException{
    public UploadFileException(String errorMessage) {
        super(errorMessage);
    }
}
