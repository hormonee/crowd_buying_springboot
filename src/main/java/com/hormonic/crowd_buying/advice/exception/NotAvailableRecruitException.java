package com.hormonic.crowd_buying.advice.exception;

import com.hormonic.crowd_buying.advice.payload.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;

@Getter
public class NotAvailableRecruitException extends EntityNotFoundException {
    private final ErrorCode errorCode;

    public NotAvailableRecruitException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
