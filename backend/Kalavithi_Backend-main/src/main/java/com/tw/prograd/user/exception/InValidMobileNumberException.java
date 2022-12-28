package com.tw.prograd.user.exception;

public class InValidMobileNumberException extends RuntimeException{

    private String message;

    public InValidMobileNumberException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
