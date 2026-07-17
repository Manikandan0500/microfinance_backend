package com.bbots.mfin.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends RuntimeException {
    private final HttpStatus status;

    public InvalidCredentialsException(String message) {
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
    }

    public InvalidCredentialsException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}