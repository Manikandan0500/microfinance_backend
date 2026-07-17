package com.bbots.mfin.exception;

import org.springframework.http.HttpStatus;

public class UserBlockedException extends RuntimeException {
    private final HttpStatus status;

    public UserBlockedException(String message) {
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
    }

    public UserBlockedException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
