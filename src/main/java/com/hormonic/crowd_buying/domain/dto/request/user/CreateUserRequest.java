package com.hormonic.crowd_buying.domain.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CreateUserRequest {
    @NotBlank(message = "아이디는 필수 입력 사항입니다.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    private String userPw;

    @NotBlank(message = "이름은 필수 입력 사항입니다.")
    private String userName;

    @NotBlank(message = "생년월일은 필수 입력 사항입니다.")
    private String userBirth;

    @NotBlank(message = "연락처는 필수 입력 사항입니다.")
    private String userContact;

    @NotBlank(message = "주소는 필수 입력 사항입니다.")
    private String userAddress;

    @NotBlank(message = "이메일은 필수 입력 사항입니다.")
    private String userEmail;

    @NotBlank(message = "성별은 필수 입력 사항입니다.")
    private String userGender;
}
