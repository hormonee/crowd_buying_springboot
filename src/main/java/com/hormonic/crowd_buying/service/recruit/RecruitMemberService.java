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

    // 특정 리크루트 참여 인원 목록 아이디만 리스트로 반환
    public List<String> getRecruitMemberByRecruitUuid(UUID recruitUuid) {
        return recruitMemberRepository.findAllByRecruitMemberPK_RecruitUuid(recruitUuid).stream()
                .map(i -> i.toGetRecruitMemberResponse(i).getUserId())
                .collect(Collectors.toList());
    }

    // 리크루트 타입(A:익명, O:공개)에 따른 참여 인원 목록 반환 (익명의 경우 ID 대신 "익명 회원 n"으로 표시)
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

    // 사용자가 리크루트에 참여 중인지 판별
    public boolean isParticipatingRecruit(UpdateRecruitRequest updateRecruitRequest) {
        return recruitMemberRepository.existsByRecruitMemberPK(
                RecruitMemberPK.builder()
                               .recruitUuid(updateRecruitRequest.getRecruitUuid())
                               .userId(updateRecruitRequest.getUserId())
                               .build() );
    }

    // 특정 리크루트 참여 인원 수 반환
    public int getNumberOfRecruitMemberParticipated(UUID recruitUuid) {
        return recruitMemberRepository.countByRecruitMemberPK_RecruitUuid(recruitUuid);
    }

    // 특정 사용자가 참여 중인 모든 리크루트 리스트 반환
    public List<RecruitMember> getParticipatingRecruitByUserId(String userId) {
        return recruitMemberRepository.findAllByRecruitMemberPK_UserId(userId);
    }

    // 리크루트 멤버 생성 (리크루트 개설 또는 참여)
    @Transactional
    public void createRecruitMember(UpdateRecruitRequest createRecruitMemberRequest) {
        RecruitMemberPK recruitMemberPK = new RecruitMemberPK(
                createRecruitMemberRequest.getRecruitUuid(),
                createRecruitMemberRequest.getUserId());

        recruitMemberRepository.save(new RecruitMember(recruitMemberPK));
    }

    // 한 명의 사용자가 참여 중인 리크루트 취소 시, 관련 리크루트 멤버 엔티티 정보 삭제
    @Transactional
    public void deleteRecruitMember(UpdateRecruitRequest deleteRecruitMemberRequest) {
        recruitMemberRepository.deleteById(new RecruitMemberPK(deleteRecruitMemberRequest.getRecruitUuid(),
                                                               deleteRecruitMemberRequest.getUserId()));
    }

    // 리크루트 철회 시, 관련 모든 리크루트 멤버 엔티티 정보 삭제
    @Transactional
    public void deleteAllRecruitMemberByRecruitUuid(UUID recruitUuid) {
        recruitMemberRepository.deleteAllByRecruitMemberPK_RecruitUuid(recruitUuid);
    }
}
