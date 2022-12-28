package com.tw.prograd.user.exception;

public class ConfirmNewPasswordDoesNotMatchException extends RuntimeException{

    public ConfirmNewPasswordDoesNotMatchException(String message){
        super(message);
    }
}
