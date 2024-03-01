package com.hormonic.crowd_buying.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, columnDefinition = "BINARY(16)")
    @Comment("유저 UUID")
    private UUID userUuid;

    @Column(nullable = false)
    @Comment("유저 이름")
    private String userName;

    @Column(nullable = false)
    @Comment("유저 이메일")
    private String userEmail;

    @CreationTimestamp
    @Column(nullable = false)
    @Comment("생성 날짜")
    private LocalDateTime createdDate;
}

/*@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    @Comment("유저 UUID")
    private UUID userUuid;

    @Column(nullable = false)
    @Comment("유저 ID")
    private String userId;

    @Column(nullable = false)
    @Comment("유저 PW")
    private String userPw;

    @Column(nullable = false)
    @Comment("유저 이름")
    private String userName;

    @Column(nullable = false)
    @Comment("유저 생년월일")
    private String userBitrh;

    @Column(nullable = false)
    @Comment("유저 연락처")
    private String userContact;

    @Column(nullable = false)
    @Comment("유저 주소")
    private String userAddress;

    @Column(nullable = false)
    @Comment("유저 이메일")
    private String userEmail;

    @Column(nullable = false)
    @Comment("유저 성별")
    private String userGender;

    @Column(nullable = false)
    @Comment("유저 가입일")
    private LocalDateTime userRegdate;

}*/
