package com.bbots.mfin.exception;

public class PasswordReuseException extends RuntimeException {

    private String errorCode;

    public PasswordReuseException(String message) {
        super(message);
    }

    public PasswordReuseException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
