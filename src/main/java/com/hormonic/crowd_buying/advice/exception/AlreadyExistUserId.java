package com.hormonic.crowd_buying.advice.exception;

import com.hormonic.crowd_buying.advice.payload.ErrorCode;
import lombok.Getter;

@Getter
public class AlreadyExistUserId extends RuntimeException {
    private final ErrorCode errorCode;

    public AlreadyExistUserId(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
