package com.hormonic.crowd_buying.advice.exception;

import com.hormonic.crowd_buying.advice.payload.ErrorCode;
import lombok.Getter;

import java.io.IOException;

@Getter
public class AwsS3UploadFailException extends IOException {
    private final ErrorCode errorCode;

    public AwsS3UploadFailException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
