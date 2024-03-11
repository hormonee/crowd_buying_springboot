package com.hormonic.crowd_buying.advice;

import com.hormonic.crowd_buying.advice.exception.*;
import com.hormonic.crowd_buying.advice.payload.ErrorCode;
import com.hormonic.crowd_buying.advice.payload.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice(annotations = RestController.class)
public class ControllerAdvice {

    @ExceptionHandler(DefaultException.class)
    public ResponseEntity<ErrorResponse> handleDefaultException(DefaultException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = errorCode.toErrorResponse();

        return new ResponseEntity<>(errorResponse, errorCode.toHttpStatus());
    }

    @ExceptionHandler(NotEnoughAllotmentException.class)
    public ResponseEntity<ErrorResponse> handleNotEnoughAllotmentException(NotEnoughAllotmentException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = errorCode.toErrorResponse();

        return new ResponseEntity<>(errorResponse, errorCode.toHttpStatus());
    }

     @ExceptionHandler(NotAvailableRecruitException.class)
     public ResponseEntity<ErrorResponse> handleNotAvailableRecruitException(NotAvailableRecruitException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = errorCode.toErrorResponse();

        return new ResponseEntity<>(errorResponse, errorCode.toHttpStatus());
     }

    @ExceptionHandler(AwsS3UploadFailException.class)
    public ResponseEntity<ErrorResponse> handleAwsS3UploadFailException(AwsS3UploadFailException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = errorCode.toErrorResponse();

        return new ResponseEntity<>(errorResponse, errorCode.toHttpStatus());
    }

    @ExceptionHandler(AlreadyRecruitMemberException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyRecruitMemberException(AlreadyRecruitMemberException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = errorCode.toErrorResponse();

        return new ResponseEntity<>(errorResponse, errorCode.toHttpStatus());
    }

    @ExceptionHandler(NotRecruitMemberException.class)
    public ResponseEntity<ErrorResponse> handleNotRecruitMemberException(NotRecruitMemberException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = errorCode.toErrorResponse();

        return new ResponseEntity<>(errorResponse, errorCode.toHttpStatus());
    }

    @ExceptionHandler(NotAcceptedRecruitException.class)
    public ResponseEntity<ErrorResponse> handleNotAcceptedRecruitException(NotAcceptedRecruitException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = errorCode.toErrorResponse();

        return new ResponseEntity<>(errorResponse, errorCode.toHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException() {
        ErrorCode errorCode = ErrorCode.NOT_VALIDATED_PARAM;
        ErrorResponse errorResponse = errorCode.toErrorResponse();

        return new ResponseEntity<>(errorResponse, errorCode.toHttpStatus());
    }

    @ExceptionHandler(AlreadyExistUserId.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExistUserId() {
        ErrorCode errorCode = ErrorCode.ALREADY_EXIST_USERID;
        ErrorResponse errorResponse = errorCode.toErrorResponse();

        return new ResponseEntity<>(errorResponse, errorCode.toHttpStatus());
    }

}
