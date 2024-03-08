package com.hormonic.crowd_buying.domain.dto.response.user;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CreateAndDeleteUserResponse {
    private String userName;
    private String userId;
}
