package com.hormonic.crowd_buying.domain.dto.response.recruit;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GetRecruitMemberResponse {
    private String userId;
}
