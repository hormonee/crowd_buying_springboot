package com.hormonic.crowd_buying.domain.dto.request.recruit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateRecruitRequest {
    @NotBlank(message = "아이디는 필수 입력 사항입니다.")
    private String userId;

    @NotBlank(message = "리크루트 타입은 필수 선택 사항입니다.")
    private String recruitType;

    @NotNull(message = "리크루트 총 모집원 수는 필수 입력 사항입니다.")
    private Integer recruitMemberTotal;

    @NotBlank(message = "리크루트 제목은 필수 입력 사항입니다.")
    private String recruitTitle;

    @NotNull(message = "리크루트 카테고리는 필수 선택 사항입니다.")
    private Integer categoryId;

    @NotNull(message = "리크루트 모금액은 필수 선택 사항입니다.")
    private Integer recruitPrice;
}
