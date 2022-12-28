package com.tw.prograd.user.exception;

public class CurrentPasswordSameAsNewPasswordException extends RuntimeException {
    public CurrentPasswordSameAsNewPasswordException(String message) {
        super(message);
    }
}
