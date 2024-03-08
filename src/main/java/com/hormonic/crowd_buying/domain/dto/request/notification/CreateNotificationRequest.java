package com.hormonic.crowd_buying.domain.dto.request.notification;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateNotificationRequest {
    @NotBlank(message = "알림을 받을 유저 아이디는 필수 입력 사항입니다.")
    private String userId;

    @NotBlank(message = "알림 제목은 필수 입력 사항입니다.")
    private String notificationTitle;

    @NotBlank(message = "알림 내용은 필수 입력 사항입니다.")
    private String notificationContent;
}
