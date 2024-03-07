package com.hormonic.crowd_buying.domain.dto.request.bookmark;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateBookmarkRequest {
    @NotBlank(message = "사용자 ID는 필수 입력 사항입니다.")
    private String userId;

    @NotNull(message = "리크루트 UUID는 필수 입력 사항입니다.")
    private UUID recruitUuid;

    @NotBlank(message = "리크루트 제목은 필수 입력 사항입니다.")
    private String recruitTitle;

    @NotBlank(message = "리크루트 대표 이미지 URL 경로는 필수 입력 사항입니다.")
    private String recruitImagePath;

    @NotNull(message = "련재 리크루트 참여 인원 수는 필수 입력 사항입니다.")
    private Integer recruitMemberParticipated;

    @NotNull(message = "리크루트 총 모집원 수는 필수 입력 사항입니다.")
    private Integer recruitMemberTotal;
}
