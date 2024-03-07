package com.hormonic.crowd_buying.domain.entity.recruit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class RecruitMemberPK implements Serializable {
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    @Comment("리크루트 UUID, 유저 ID와 함께 복합키")
    private UUID recruitUuid;

    @Column(nullable = false)
    @Comment("유저 ID, 리크루트 UUID와 함께 복합키")
    private String userId;
}
