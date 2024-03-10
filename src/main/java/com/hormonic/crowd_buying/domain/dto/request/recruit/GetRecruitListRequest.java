package com.hormonic.crowd_buying.domain.dto.request.recruit;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetRecruitListRequest {
    private Integer categoryId;
    private String recruitTitle = "";
    private String orderBy;
    private String isEnded = "A";  // E: end, N: not end, A: All (end + not end)
    private String examinationResult = "";
}
