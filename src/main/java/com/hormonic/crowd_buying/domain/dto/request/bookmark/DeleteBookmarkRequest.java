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
public class DeleteBookmarkRequest {
    @NotBlank(message = "사용자 ID는 필수 입력 사항입니다.")
    private String userId;

    @NotNull(message = "리크루트 UUID는 필수 입력 사항입니다.")
    private UUID recruitUuid;
}
