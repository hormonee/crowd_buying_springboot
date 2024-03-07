package com.hormonic.crowd_buying.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Administrator {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    @Comment("관리자 UUID")
    private UUID adminUuid;

    @Column(nullable = false)
    @Comment("관리자 ID")
    private String adminId;

    @Column(nullable = false)
    @Comment("관리자 비밀번호")
    private String adminPw;

    @Column(nullable = false)
    @Comment("관리자 이름")
    private String adminName;

    @Column(nullable = false)
    @Comment("관리자 연락처")
    private String adminContact;

    @Column(nullable = false)
    @Comment("관리자 주소")
    private String adminAddress;

    @Column(nullable = false)
    @Comment("관리자 이메일")
    private String adminEmail;

    @CreationTimestamp
    @Column(nullable = false)
    @Comment("관리자 등록 날짜")
    private LocalDateTime adminRegDate;

    public Administrator(String adminId, String adminPw, String adminName, String adminContact, String adminAddress, String adminEmail) {
        this.adminId = adminId;
        this.adminPw = adminPw;
        this.adminName = adminName;
        this.adminContact = adminContact;
        this.adminAddress = adminAddress;
        this.adminEmail = adminEmail;
    }
}
