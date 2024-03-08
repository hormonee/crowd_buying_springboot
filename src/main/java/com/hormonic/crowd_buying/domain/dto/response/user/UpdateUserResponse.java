package com.hormonic.crowd_buying.domain.dto.response.user;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UpdateUserResponse {
    private String userId;
    private String userPw;
    private String userName;
    private String userContact;
    private String userAddress;
    private String userEmail;
}
