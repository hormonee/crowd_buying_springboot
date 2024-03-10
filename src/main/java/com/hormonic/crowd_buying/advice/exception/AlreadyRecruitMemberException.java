package com.hormonic.crowd_buying.advice.exception;

import com.hormonic.crowd_buying.advice.payload.ErrorCode;
import lombok.Getter;

@Getter
public class AlreadyRecruitMemberException extends IllegalArgumentException {
    private final ErrorCode errorCode;

    public AlreadyRecruitMemberException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
