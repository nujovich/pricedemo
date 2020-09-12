package com.bitcoindemo.exception;

public class PriceNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;
    private String message;

    public PriceNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}