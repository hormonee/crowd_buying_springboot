package com.hormonic.crowd_buying.domain.dto.request.recruit;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ExamineRecruitRequest {
    @NotBlank(message = "리크루트 등록 심사 결과는 필수 입력 사항입니다.")
    private String examinationResult;

    @NotBlank(message = "리크루트 등록 심사 담당자는 필수 입력 사항입니다.")
    private String adminId;
}
