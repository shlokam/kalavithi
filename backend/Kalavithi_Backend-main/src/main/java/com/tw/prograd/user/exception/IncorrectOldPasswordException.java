package com.tw.prograd.user.exception;

public class IncorrectOldPasswordException extends RuntimeException {
    public IncorrectOldPasswordException(String message) {
        super(message);
    }
}
