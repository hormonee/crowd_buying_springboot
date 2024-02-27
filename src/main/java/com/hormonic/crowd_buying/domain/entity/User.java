package com.hormonic.crowd_buying.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Comment("유저 UUID")
    private Byte[] userUuid;

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

}
