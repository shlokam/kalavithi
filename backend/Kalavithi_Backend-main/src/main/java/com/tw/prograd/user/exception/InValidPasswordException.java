package com.tw.prograd.user.exception;

public class InValidPasswordException extends RuntimeException {
    private String message;

    public InValidPasswordException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
