package com.hormonic.crowd_buying.advice;

import com.hormonic.crowd_buying.advice.exception.DefaultException;
import com.hormonic.crowd_buying.advice.exception.NotAvailableRecruitException;
import com.hormonic.crowd_buying.advice.exception.NotEnoughAllotmentException;
import com.hormonic.crowd_buying.advice.payload.ErrorCode;
import com.hormonic.crowd_buying.advice.payload.ErrorResponse;
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

     @ExceptionHandler(NotAvailableRecruitException.class)
     public ResponseEntity<ErrorResponse> handleNotAvailableRecruitException(NotAvailableRecruitException e) {
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

}
