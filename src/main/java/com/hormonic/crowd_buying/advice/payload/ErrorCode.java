package com.hormonic.crowd_buying.advice.payload;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    NOT_ENOUGH_ALLOTMENT(400, "EC001", "분담금이 5만원 미만입니다."),
    NOT_AVAILABLE_RECRUIT(403, "EC002", "유효하지 않은 리크루트입니다."),
    S3_UPLOAD_FAIL(503, "EC003", "파일을 AWS S3에 업로드하는데 실패했습니다."),
    ALREADY_RECRUIT_MEMBER(400, "EC004", "이미 리크루트 멤버입니다."),
    NOT_RECRUIT_MEMBER(400, "EC005", "리크루트 멤버만 참여 취소가 가능합니다."),
    NOT_ACCEPTED_RECRUIT(400, "EC006", "심사 통과된 리크루트가 아닙니다."),
    NOT_VALIDATED_PARAM(400, "EC007", "파라미터가 유효하지 않습니다."),
    ALREADY_EXIST_USERID(409, "EC008", "이미 존재하는 아이디입니다.");


    private final int status;
    private final String code;
    private String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public ErrorResponse toErrorResponse() {
        return ErrorResponse.builder()
                .status(this.status)
                .code(this.code)
                .message(this.message)
                .build();
    }

    public HttpStatusCode toHttpStatus() {
        return HttpStatusCode.valueOf(this.status);
    }

    public void changeErrorMessage(String errorMessage) {
        this.message = errorMessage;
    }
}
