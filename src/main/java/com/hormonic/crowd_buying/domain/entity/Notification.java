package com.hormonic.crowd_buying.domain.entity;

import com.hormonic.crowd_buying.domain.dto.response.notification.GetNotificationListOfUserResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Comment("알림 ID")
    private int notificationNo;

    @Column(nullable = false)
    @Comment("사용자 ID")
    private String userId;

    @Column(nullable = false)
    @Comment("알림 제목")
    private String notificationTitle;

    @Column(nullable = false)
    @Comment("알림 내용")
    private String notificationContent;

    @CreationTimestamp
    @Column(nullable = false)
    @Comment("알림 발송 일시")
    private LocalDateTime notificationSendDate;

    @Column(nullable = true)
    @Comment("알림 확인 일시")
    private LocalDateTime notificationCheckDate;

    public Notification(String userId, String notificationTitle, String notificationContent) {
        this.userId = userId;
        this.notificationTitle = notificationTitle;
        this.notificationContent = notificationContent;
    }

    public GetNotificationListOfUserResponse toGetNotificationListOfUserResponse(Notification notification) {
        // builder 로 생성해도 Auto Increment 적용됨?

        return GetNotificationListOfUserResponse.builder()
                .notificationNo(notification.getNotificationNo())
                .notificationTitle(notification.getNotificationTitle())
                .notificationContent(notification.getNotificationContent())
                .notificationSendDate(notification.getNotificationSendDate())
                .notificationCheckDate(notification.getNotificationCheckDate())
                .build();
    }
}
