package com.bitcoindemo.exception;

public class DatetimeFormatException extends Exception {

    private static final long serialVersionUID = 1L;
    private String message;

    public DatetimeFormatException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}