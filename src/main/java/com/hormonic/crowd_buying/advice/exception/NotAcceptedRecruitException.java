package com.hormonic.crowd_buying.advice.exception;

import com.hormonic.crowd_buying.advice.payload.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;

@Getter
public class NotAcceptedRecruitException extends EntityNotFoundException {
    private ErrorCode errorCode;

    public NotAcceptedRecruitException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
