package com.hormonic.crowd_buying.domain.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DeleteUserRequest {
    @NotBlank
    private String userId;
}
