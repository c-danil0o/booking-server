package com.komsije.booking.exceptions;

public class AccountNotActivatedException extends RuntimeException {
    public AccountNotActivatedException(String errorMessage) {
        super(errorMessage);
    }
}
