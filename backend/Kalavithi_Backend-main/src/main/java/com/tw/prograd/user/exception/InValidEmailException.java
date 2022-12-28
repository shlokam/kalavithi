package com.tw.prograd.user.exception;

public class InValidEmailException extends RuntimeException{
    private String message;

    public InValidEmailException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
