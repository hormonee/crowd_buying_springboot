package com.hormonic.crowd_buying.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AwsS3 {
    private String key;
    private String path;
}
