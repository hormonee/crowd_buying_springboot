package com.hormonic.crowd_buying.repository;

import com.hormonic.crowd_buying.domain.entity.recruit.Recruit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface RecruitRepository extends JpaRepository<Recruit, UUID> {
//    List<Recruit> findAll(Specification<Recruit> spec, Sort sort);
    Page<Recruit> findAll(Specification<Recruit> spec, Pageable pageable);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Recruit r set r.examinationResult = :examinationResult, r.adminId = :adminId, r.recruitExaminedDate = now() " +
            "where r.recruitUuid = :recruitUuid")
    void examineRecruit(@Param("recruitUuid") UUID recruitUuid,
                       @Param("examinationResult") String examinationResult,
                       @Param("adminId") String adminId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Recruit r set r.recruitHit = r.recruitHit + 1 where r.recruitUuid = :recruitUuid")
    int clickRecruit(@Param("recruitUuid") UUID recruitUuid);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Recruit r set r.recruitBookmarked = r.recruitBookmarked + 1 where r.recruitUuid = :recruitUuid")
    void plusRecruitBookmarked(@Param("recruitUuid") UUID recruitUuid);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Recruit r set r.recruitBookmarked = r.recruitBookmarked - 1 where r.recruitUuid = :recruitUuid")
    void minusRecruitBookmarked(@Param("recruitUuid") UUID recruitUuid);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Recruit r set r.recruitMemberParticipated = r.recruitMemberParticipated + 1 " +
            "where r.recruitUuid = :recruitUuid")
    void participateRecruit(@Param("recruitUuid") UUID recruitUuid);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Recruit r set r.recruitMemberParticipated = r.recruitMemberParticipated - 1 " +
            "where r.recruitUuid = :recruitUuid")
    void cancelParticipateRecruit(@Param("recruitUuid") UUID recruitUuid);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Recruit r set r.recruitEndDate = now() where r.recruitUuid = :recruitUuid")
    void closeRecruit(@Param("recruitUuid") UUID recruitUuid);

}
