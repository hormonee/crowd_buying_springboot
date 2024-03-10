package com.hormonic.crowd_buying.advice.exception;

import com.hormonic.crowd_buying.advice.payload.ErrorCode;
import lombok.Getter;

@Getter
public class NotRecruitMemberException extends IllegalArgumentException{
    private final ErrorCode errorCode;

    public NotRecruitMemberException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
