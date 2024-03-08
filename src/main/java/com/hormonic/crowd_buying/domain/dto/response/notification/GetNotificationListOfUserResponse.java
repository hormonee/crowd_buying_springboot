package com.hormonic.crowd_buying.domain.dto.response.notification;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GetNotificationListOfUserResponse {
    private Integer notificationNo;
    private String notificationTitle;
    private String notificationContent;
    private LocalDateTime notificationSendDate;
    private LocalDateTime notificationCheckDate;
}
