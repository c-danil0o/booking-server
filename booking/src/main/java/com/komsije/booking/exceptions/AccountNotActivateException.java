package com.komsije.booking.exceptions;

public class AccountNotActivateException extends RuntimeException {
    public AccountNotActivateException(String errorMessage) {
        super(errorMessage);
    }
}
