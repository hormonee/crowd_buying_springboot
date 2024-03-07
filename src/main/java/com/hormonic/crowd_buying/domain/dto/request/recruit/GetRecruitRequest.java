package com.hormonic.crowd_buying.domain.dto.request.recruit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetRecruitRequest {
    private Integer categoryId = 0;
    private String recruitTitle = "";
    private String orderBy;
}
