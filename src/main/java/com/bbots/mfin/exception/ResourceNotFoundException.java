package com.bbots.mfin.exception;

import org.springframework.http.HttpStatus;
 
public class ResourceNotFoundException extends RuntimeException {
    private final HttpStatus status;
 
    public ResourceNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
    }
 
    public ResourceNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
 
    public HttpStatus getStatus() {
        return status;
    }
}
 
