package com.hormonic.crowd_buying.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserResponse {
    private String userId;
    private String userPw;
    private String userName;
    private String userContact;
    private String userAddress;
    private String userEmail;
}
