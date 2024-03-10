package com.hormonic.crowd_buying.advice.payload;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private int status;
    private String code;
    private String message;
    private LocalDateTime timestamp;
}
