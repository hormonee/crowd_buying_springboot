package com.hormonic.crowd_buying.advice.exception;

import com.hormonic.crowd_buying.advice.payload.ErrorCode;
import lombok.Getter;

@Getter
public class AlreadyExistUserIdException extends RuntimeException {
    private final ErrorCode errorCode;

    public AlreadyExistUserIdException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
