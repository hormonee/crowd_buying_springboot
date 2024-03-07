package com.hormonic.crowd_buying.domain.dto.response.user;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateAndDeleteUserResponse {
    private String userName;
    private String userId;
}
