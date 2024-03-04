package com.hormonic.crowd_buying.domain.entity;

import com.hormonic.crowd_buying.domain.dto.response.CreateAndDeleteUserResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    @Comment("사용자 UUID")
    private UUID userUuid;

    @Column(nullable = false)
    @Comment("사용자 ID")
    private String userId;

    @Column(nullable = false)
    @Comment("사용자 비밀번호")
    private String userPw;

    @Column(nullable = false)
    @Comment("사용자 이름")
    private String userName;

    @Column(nullable = false)
    @Comment("사용자 생년월일")
    private String userBirth;

    @Column(nullable = false)
    @Comment("사용자 연락처")
    private String userContact;

    @Column(nullable = false)
    @Comment("사용자 주소")
    private String userAddress;

    @Column(nullable = false)
    @Comment("사용자 이메일")
    private String userEmail;

    @Column(nullable = false)
    @Comment("사용자 성별")
    private String userGender;

    @CreationTimestamp
    @Column(nullable = false)
    @Comment("생성 날짜")
    private LocalDateTime userRegDate;

    public User(String userId, String userPw, String userName, String userBirth, String userContact, String userAddress, String userEmail, String userGender) {
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.userBirth = userBirth;
        this.userContact = userContact;
        this.userAddress = userAddress;
        this.userEmail = userEmail;
        this.userGender = userGender;
    }

    public CreateAndDeleteUserResponse toCreateAndDeleteUserResponse() {
        return CreateAndDeleteUserResponse.builder()
                .userName(this.getUserName())
                .userId(this.getUserId())
                .build();
    }
}
