package com.hormonic.crowd_buying.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Comment("카테고리 ID")
    private int categoryId;

    @Column(nullable = false)
    @Comment("카테고리 그룹명")
    private String categoryGroupId;

    @Column(nullable = false)
    @Comment("카테고리 단계")
    private int categoryLv;

    @Column(nullable = false)
    @Comment("분류 (대, 중, 소)")
    private String categoryNm;

    @Column(nullable = false)
    @Comment("카테고리 세부 단계")
    private int categoryDetailLv;

    @Column(nullable = false)
    @Comment("카테고리 세부 단계 이름")
    private String categoryDetailNm;

    @Column(nullable = false)
    @Comment("카테고리 상위 분류 단계")
    private int categoryParentLv;

    @Column(nullable = false)
    @Comment("카테고리 상위 분류 세부 단계")
    private int categoryParentDetailLv;
}
