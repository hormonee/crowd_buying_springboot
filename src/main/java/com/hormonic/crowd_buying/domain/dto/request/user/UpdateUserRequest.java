package com.hormonic.crowd_buying.domain.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateUserRequest {
    @NotBlank(message = "아이디는 필수 입력 사항입니다.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    private String userPw;

    @NotBlank(message = "연락처는 필수 입력 사항입니다.")
    private String userContact;

    @NotBlank(message = "주소는 필수 입력 사항입니다.")
    private String userAddress;

    @NotBlank(message = "이메일은 필수 입력 사항입니다.")
    private String userEmail;
}