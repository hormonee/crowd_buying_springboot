package com.hormonic.crowd_buying.domain.dto.request.recruit;

import lombok.*;

@Getter
@Setter
@Builder
public class GetRecruitListRequest {
    private Integer categoryId;
    private String recruitTitle;
    private String orderBy;
    private String isEnded;  // E: end, N: not end
    private String examinationResult;
}
