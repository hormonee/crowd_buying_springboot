package com.hormonic.crowd_buying.domain.dto.response.recruit;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetRecruitMemberResponse {
    private String userId;
}
