package com.hormonic.crowd_buying.service.user;

import com.hormonic.crowd_buying.advice.exception.AlreadyExistUserIdException;
import com.hormonic.crowd_buying.advice.payload.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    // 회원 가입 시, 이미 존재하는 아이디인 경우 예외 발생
    public void validateIsUserIdExist(boolean isUserIdExist) {
        if(isUserIdExist) throw new AlreadyExistUserIdException(ErrorCode.ALREADY_EXIST_USERID);
    }

}
