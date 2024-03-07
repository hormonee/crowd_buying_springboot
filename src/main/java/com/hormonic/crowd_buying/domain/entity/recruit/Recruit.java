package com.hormonic.crowd_buying.domain.entity.recruit;

import com.hormonic.crowd_buying.domain.dto.response.recruit.CreateAndDeleteRecruitResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@DynamicInsert
public class Recruit {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    @Comment("리크루트 UUID")
    private UUID recruitUuid;

    @Column(nullable = false)
    @Comment("리크루트 생성자 ID")
    private String userId;

    @Column(nullable = false)
    @Comment("리크루트 타입 (O: Open, A: Anonymous)")
    private String recruitType;

    @Column(nullable = false)
    @Comment("현재 리크루트 참여 인원 수")
    private Integer recruitMemberParticipated;

    @Column(nullable = false)
    @Comment("리크루트 총 모집원 수")
    private Integer recruitMemberTotal;

    @Column(nullable = false)
    @Comment("리크루트 제목")
    private String recruitTitle;

    @Column(nullable = false)
    @Comment("리크루트 카테고리 ID")
    private Integer categoryId;

    @Column(nullable = false)
    @Comment("리크루트 모금액")
    private Integer recruitPrice;

    @Column(nullable = false)
    @Comment("리크루트 대표 이미지 URL 경로")
    private String recruitImagePath;

    @Column(nullable = false)
    @Comment("리크루트 관심 수")
    private Integer recruitBookmarked;

    @Column(nullable = false)
    @Comment("리크루트 조회 수")
    private Integer recruitHit;

    @CreationTimestamp
    @Column(nullable = false)
    @Comment("리크루트 생성 날짜")
    private LocalDateTime recruitRegDate;

    @Column(nullable = false)
    @ColumnDefault("O")
    @Builder.Default
    @Comment("리크루트 등록 심사 결과 (O: Ongoing, A: Accepted, D: Denied)")
    private String examinationResult = "O";

    @Column(nullable = true)
    @Comment("리크루트 등록 심사 일시")
    private LocalDateTime recruitExaminedDate;

    @Column(nullable = true)
    @Comment("리크루트 등록 심사 담당자")
    private String adminId;

    @Column(nullable = true)
    @Comment("리크루트 종료 날짜")
    private LocalDateTime recruitEndDate;

    public Recruit(String userId, String recruitType, int recruitMemberTotal, String recruitTitle, int categoryId, int recruitPrice, String recruitImagePath) {
        this.userId = userId;
        this.recruitType = recruitType;
        this.recruitMemberTotal = recruitMemberTotal;
        this.recruitTitle = recruitTitle;
        this.categoryId = categoryId;
        this.recruitPrice = recruitPrice;
        this.recruitImagePath = recruitImagePath;
    }

    public CreateAndDeleteRecruitResponse toCreateAndDeleteRecruitResponse() {
        return CreateAndDeleteRecruitResponse.builder()
                .recruitTitle(this.getRecruitTitle())
                .build();
    }
}
