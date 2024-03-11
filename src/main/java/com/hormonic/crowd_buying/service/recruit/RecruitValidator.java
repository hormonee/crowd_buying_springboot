package com.hormonic.crowd_buying.service.recruit;

import com.hormonic.crowd_buying.advice.exception.*;
import com.hormonic.crowd_buying.advice.payload.ErrorCode;
import com.hormonic.crowd_buying.domain.dto.request.recruit.CreateRecruitRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RecruitValidator {
    // 마감되거나 철회된 리크루트인지 검증
    public void validateIsAvailable(LocalDateTime timestamp) {
        if (timestamp != null) throw new NotAvailableRecruitException(ErrorCode.NOT_AVAILABLE_RECRUIT);
    }

    // 분담금이 5만원 이상인지 검증
    public void validateAllotment(CreateRecruitRequest createRecruitRequest) {
        if (createRecruitRequest.getRecruitPrice() / createRecruitRequest.getRecruitMemberTotal() < 50000) {
            throw new NotEnoughAllotmentException(ErrorCode.NOT_ENOUGH_ALLOTMENT);
        }
    }

    // 리크루트 participate 처리 시, 참여 중이 아닌 상태인지 검증
    public void validateIsMemberOfRecruit(boolean isMemberOfRecruit) {
        if (isMemberOfRecruit) {
            throw new AlreadyRecruitMemberException(ErrorCode.ALREADY_RECRUIT_MEMBER);
        }
    }

    // 리크루트 participate 취소 처리 시, 참여 중인 상태인지 검증
    public void validateIsNotMemberOfRecruit(boolean isMemberOfRecruit) {
        if (!isMemberOfRecruit) {
            throw new NotRecruitMemberException(ErrorCode.NOT_RECRUIT_MEMBER);
        }
    }

    // 리크루트 등록 심사 통과 여부 검증
    public void validateIsAcceptedRecruit(String examinationResult) {
        if(!examinationResult.equals("A")) {
            throw new NotAcceptedRecruitException(ErrorCode.NOT_ACCEPTED_RECRUIT);
        }
    }

}
