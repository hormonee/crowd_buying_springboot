package com.hormonic.crowd_buying.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserRequest {
    @NotBlank
    private String userId;
}
