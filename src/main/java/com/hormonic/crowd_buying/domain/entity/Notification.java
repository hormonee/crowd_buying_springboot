package com.hormonic.crowd_buying.domain.entity;

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
}
