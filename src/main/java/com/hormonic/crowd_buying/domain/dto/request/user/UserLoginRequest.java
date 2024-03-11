package com.hormonic.crowd_buying.domain.dto.request.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {
    private String userId;
    private String userPw;
}
