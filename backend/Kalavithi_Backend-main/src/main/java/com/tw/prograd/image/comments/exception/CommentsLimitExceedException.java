package com.tw.prograd.image.comments.exception;

public class CommentsLimitExceedException extends RuntimeException {
    private String message;

    public CommentsLimitExceedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
