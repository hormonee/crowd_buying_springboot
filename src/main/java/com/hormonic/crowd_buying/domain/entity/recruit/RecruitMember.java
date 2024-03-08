package com.hormonic.crowd_buying.domain.entity.recruit;

import com.hormonic.crowd_buying.domain.dto.response.recruit.GetRecruitMemberResponse;
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
public class RecruitMember {
    @EmbeddedId
    @Column(nullable = false)
    @Comment("리크루트 UUID, 유저 ID 복합키")
    private RecruitMemberPK recruitMemberPK;

    @CreationTimestamp
    @Column(nullable = false)
    @Comment("리크루트 참여 일시")
    private LocalDateTime participate_date;

    public RecruitMember(RecruitMemberPK recruitMemberPK) {
        this.recruitMemberPK = recruitMemberPK;
    }

    public GetRecruitMemberResponse toGetRecruitMemberResponse(RecruitMember recruitMember) {
        return GetRecruitMemberResponse.builder()
                .userId(recruitMember.getRecruitMemberPK().getUserId())
                .build();
    }
}
