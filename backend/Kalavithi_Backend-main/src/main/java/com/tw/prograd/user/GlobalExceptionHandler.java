package com.tw.prograd.user;

import com.tw.prograd.dto.ErrorResponse;
import com.tw.prograd.image.comments.exception.CommentsLimitExceedException;
import com.tw.prograd.image.exception.ImageNotFoundException;
import com.tw.prograd.user.exception.InValidEmailException;
import com.tw.prograd.user.exception.InValidMobileNumberException;
import com.tw.prograd.user.exception.InValidPasswordException;
import com.tw.prograd.user.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleItemAlreadyExistsException(UserAlreadyExistException userAlreadyExist) {

        ErrorResponse errorResponse = new ErrorResponse(userAlreadyExist.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(InValidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInValidPasswordException(InValidPasswordException inValidPasswordException) {
        ErrorResponse errorResponse = new ErrorResponse(inValidPasswordException.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }

    @ExceptionHandler(InValidEmailException.class)
    public ResponseEntity<ErrorResponse> handleInValidEmailException(InValidEmailException inValidEmailException) {
        ErrorResponse errorResponse = new ErrorResponse(inValidEmailException.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }

    @ExceptionHandler(InValidMobileNumberException.class)
    public ResponseEntity<ErrorResponse> handleInValidMobileNumberException(InValidMobileNumberException inValidMobileNumberException) {
        ErrorResponse errorResponse = new ErrorResponse(inValidMobileNumberException.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        ErrorResponse errorResponse = new ErrorResponse(illegalArgumentException.getMessage());
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
    }

    @ExceptionHandler(IncorrectOldPasswordException.class)
    public ResponseEntity<ErrorResponse> handleUserPasswordDoesNotMatchException(IncorrectOldPasswordException userPasswordDoesNotMatch) {
        ErrorResponse errorResponse = new ErrorResponse(userPasswordDoesNotMatch.getMessage());
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
    }


    @ExceptionHandler(CurrentPasswordSameAsNewPasswordException.class)
    public ResponseEntity<ErrorResponse> handleCurrentPasswordSameAsNewPassword(CurrentPasswordSameAsNewPasswordException currentPasswordSameAsNewPasswordException) {
        ErrorResponse errorResponse = new ErrorResponse(currentPasswordSameAsNewPasswordException.getMessage());
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
    }

    @ExceptionHandler(ConfirmNewPasswordDoesNotMatchException.class)
    public ResponseEntity<ErrorResponse> handleConfirmNewPasswordDoesNotMatch(ConfirmNewPasswordDoesNotMatchException confirmNewPasswordDoesNotMatchException) {
        ErrorResponse errorResponse = new ErrorResponse(confirmNewPasswordDoesNotMatchException.getMessage());
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
    }

    @ExceptionHandler(CommentsLimitExceedException.class)
    public ResponseEntity<ErrorResponse> handleConfirmNewPasswordDoesNotMatch(CommentsLimitExceedException commentsLimitExceedException) {
        ErrorResponse errorResponse = new ErrorResponse(commentsLimitExceedException.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleImageNotFound(ImageNotFoundException imageNotFoundException) {
        ErrorResponse errorResponse = new ErrorResponse(imageNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
    }
}
