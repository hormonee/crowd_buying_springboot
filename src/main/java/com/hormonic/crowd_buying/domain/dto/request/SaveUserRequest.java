package com.hormonic.crowd_buying.domain.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveUserRequest {
    @NotBlank(message = "이름은 필수 입력 사항입니다.")
    private String userName;
    @NotBlank(message = "이메일은 필수 입력 사항입니다.")
    private String userEmail;
}
