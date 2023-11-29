package com.komsije.booking.exceptions;

public class AccountNotActivateException extends Exception {
    public AccountNotActivateException(String errorMessage) {
        super(errorMessage);
    }
}
