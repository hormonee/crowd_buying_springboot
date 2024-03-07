package com.hormonic.crowd_buying.domain.dto.response.category;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CategoryResponse {
    private Integer categoryId;
    private String categoryDetailNm;
}
