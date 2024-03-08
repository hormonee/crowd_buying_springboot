package com.hormonic.crowd_buying.repository;

import com.hormonic.crowd_buying.domain.dto.response.recruit.GetRecruitMemberResponse;
import com.hormonic.crowd_buying.domain.entity.recruit.RecruitMember;
import com.hormonic.crowd_buying.domain.entity.recruit.RecruitMemberPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecruitMemberRepository extends JpaRepository<RecruitMember, RecruitMemberPK> {
    List<RecruitMember> findAllByRecruitMemberPK_RecruitUuid(UUID recruitUuid);

    int countByRecruitMemberPK_RecruitUuid(UUID recruitUuid);

    List<RecruitMember> findAllByRecruitMemberPK_UserId(String userId);
}
