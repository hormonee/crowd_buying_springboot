package com.hormonic.crowd_buying.domain.dto.request.recruit;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetRecruitListRequest {
    private Integer categoryId = 0;
    private String recruitTitle = "";
    private String orderBy;
}
