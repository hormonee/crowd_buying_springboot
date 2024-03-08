package com.hormonic.crowd_buying.service.recruit;

import com.hormonic.crowd_buying.domain.dto.request.recruit.UpdateRecruitRequest;
import com.hormonic.crowd_buying.domain.entity.recruit.RecruitMember;
import com.hormonic.crowd_buying.domain.entity.recruit.RecruitMemberPK;
import com.hormonic.crowd_buying.repository.RecruitMemberRepository;
import com.hormonic.crowd_buying.repository.RecruitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruitMemberService {
    private final RecruitMemberRepository recruitMemberRepository;
    private final RecruitRepository recruitRepository;

    public List<String> getRecruitMemberByRecruitUuid(UUID recruitUuid) {
        return recruitMemberRepository.findAllByRecruitMemberPK_RecruitUuid(recruitUuid).stream()
                .map(i -> i.toGetRecruitMemberResponse(i).getUserId())
                .collect(Collectors.toList());
    }

    public List<String> getRecruitMemberByRecruitUuidConsideringRecruitType(UUID recruitUuid) {
        if(recruitRepository.findById(recruitUuid).get().getRecruitType().equals("O")) {
            return recruitMemberRepository.findAllByRecruitMemberPK_RecruitUuid(recruitUuid).stream()
                    .map(i -> i.toGetRecruitMemberResponse(i).getUserId())
                    .collect(Collectors.toList());
        } else {
            AtomicInteger index = new AtomicInteger();
            return recruitMemberRepository.findAllByRecruitMemberPK_RecruitUuid(recruitUuid).stream()
                    .map(i -> "익명 회원 " + index.getAndIncrement())
                    .collect(Collectors.toList());
        }
    }

    public int getNumberOfRecruitMemberParticipated(UUID recruitUuid) {
        return recruitMemberRepository.countByRecruitMemberPK_RecruitUuid(recruitUuid);
    }

    public List<RecruitMember> getParticipatingRecruitByUserId(String userId) {
        return recruitMemberRepository.findAllByRecruitMemberPK_UserId(userId);
    }

    @Transactional
    public void createRecruitMember(UpdateRecruitRequest createRecruitMemberRequest) {
        RecruitMemberPK recruitMemberPK = new RecruitMemberPK(
                createRecruitMemberRequest.getRecruitUuid(),
                createRecruitMemberRequest.getUserId());

        recruitMemberRepository.save(new RecruitMember(recruitMemberPK));
    }

    @Transactional
    public void deleteRecruitMember(UpdateRecruitRequest deleteRecruitMemberRequest) {
        recruitMemberRepository.deleteById(new RecruitMemberPK(deleteRecruitMemberRequest.getRecruitUuid(),
                                                               deleteRecruitMemberRequest.getUserId()));
    }

}
