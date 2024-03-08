package com.hormonic.crowd_buying.repository;

import com.hormonic.crowd_buying.domain.entity.Bookmark;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {
    List<Bookmark> findAll(Specification<Bookmark> spec, Sort sort);

    List<Bookmark> findAllByRecruitUuid(UUID recruitUuid);

    Boolean existsByUserIdAndRecruitUuid(String userId, UUID recruitUuid);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Bookmark b set b.recruitMemberParticipated = b.recruitMemberParticipated + 1 where b.recruitUuid = :recruitUuid")
    void plusRecruitMemberParticipatedOfBookmark(@Param("recruitUuid") UUID recruitUuid);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Bookmark b set b.recruitMemberParticipated = b.recruitMemberParticipated - 1 where b.recruitUuid = :recruitUuid")
    void minusRecruitMemberParticipatedOfBookmark(@Param("recruitUuid") UUID recruitUuid);

    void deleteByUserIdAndRecruitUuid(String userId, UUID recruitUuid);

    void deleteAllByRecruitUuid(UUID recruitUuid);

}
