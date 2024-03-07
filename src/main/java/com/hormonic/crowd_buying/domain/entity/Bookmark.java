package com.hormonic.crowd_buying.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.UUID;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Comment("카테고리 ID")
    private int bookmarkNo;

    @Column(nullable = false)
    @Comment("사용자 ID")
    private String userId;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    @Comment("리크루트 UUID")
    private UUID recruitUuid;

    @Column(nullable = false)
    @Comment("리크루트 제목")
    private String recruitTitle;

    @Column(nullable = false)
    @Comment("리크루트 대표 이미지 URL 경로")
    private String recruitImagePath;

    @Column(nullable = false)
    @Comment("현재 리크루트 참여 인원 수")
    private int recruitMemberParticipated;

    @Column(nullable = false)
    @Comment("리크루트 총 모집원 수")
    private int recruitMemberTotal;

    public Bookmark(String userId, UUID recruitUuid, String recruitTitle, String recruitImagePath, int recruitMemberParticipated, int recruitMemberTotal) {
        this.userId = userId;
        this.recruitUuid = recruitUuid;
        this.recruitTitle = recruitTitle;
        this.recruitImagePath = recruitImagePath;
        this.recruitMemberParticipated = recruitMemberParticipated;
        this.recruitMemberTotal = recruitMemberTotal;
    }
}
