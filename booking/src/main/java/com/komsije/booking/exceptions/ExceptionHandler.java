package com.komsije.booking.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @AllArgsConstructor
    @Data
    public static class ApiError {

        private Integer status;
        private String message;
        private String requestedUri;

    }
    @org.springframework.web.bind.annotation.ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<ApiError> resourceNotFoundException(ElementNotFoundException ex, HttpServletRequest request) {
        ApiError message = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<ApiError>(message, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IOException.class)
    public ResponseEntity<ApiError> dataNotFoundException(IOException ex, HttpServletRequest request) {
        ApiError message = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<ApiError>(message, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UploadFileException.class)
    public ResponseEntity<ApiError> uploadFileException(UploadFileException ex, HttpServletRequest request) {
        ApiError message = new ApiError(HttpStatus.I_AM_A_TEAPOT.value(), ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<ApiError>(message, HttpStatus.I_AM_A_TEAPOT);
    }



}
