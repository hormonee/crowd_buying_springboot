package com.hormonic.crowd_buying.service.recruit;

import com.hormonic.crowd_buying.domain.dto.request.recruit.CreateRecruitRequest;
import com.hormonic.crowd_buying.domain.dto.request.recruit.ExamineRecruitRequest;
import com.hormonic.crowd_buying.domain.dto.request.recruit.GetRecruitListRequest;
import com.hormonic.crowd_buying.domain.dto.request.recruit.UpdateRecruitRequest;
import com.hormonic.crowd_buying.domain.dto.response.recruit.CreateAndDeleteRecruitResponse;
import com.hormonic.crowd_buying.domain.entity.Notification;
import com.hormonic.crowd_buying.domain.entity.recruit.Recruit;
import com.hormonic.crowd_buying.repository.NotificationRepository;
import com.hormonic.crowd_buying.service.bookmark.BookmarkService;
import com.hormonic.crowd_buying.util.specification.JpaSpecification;
import com.hormonic.crowd_buying.repository.BookmarkRepository;
import com.hormonic.crowd_buying.repository.RecruitRepository;
import com.hormonic.crowd_buying.service.aws.AwsS3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruitService {
    private final RecruitRepository recruitRepository;
    private final RecruitMemberService recruitMemberService;
    private final AwsS3Service awsS3Service;
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkService bookmarkService;
    private final NotificationRepository notificationRepository;

    @Transactional
    public Optional<Recruit> getRecruitByRecruitUuid(UUID recruitUuid) {
        recruitRepository.clickRecruit(recruitUuid);
        return recruitRepository.findById(recruitUuid);
    }

    public List<Recruit> getRecruitList(GetRecruitListRequest getRecruitListRequest) {
        // Pageable pageable = PageRequest.ofSize(2).withPage(1).withSort(sort);
        Specification<Recruit> spec;
        if(getRecruitListRequest.getCategoryId() == 0) {
            spec = JpaSpecification.notEndedRecruit()
                    .and( JpaSpecification.recruitTitleLike(getRecruitListRequest.getRecruitTitle()) );
        } else {
            spec = JpaSpecification.notEndedRecruit()
                    .and( JpaSpecification.recruitCategoryLike(getRecruitListRequest.getCategoryId())
                    .and( JpaSpecification.recruitTitleLike(getRecruitListRequest.getRecruitTitle()) ));
        }

        String orderBy = getRecruitListRequest.getOrderBy();
        Sort sort = null;
        if(orderBy == null) sort = Sort.by( Sort.Order.desc("recruitBookmarked"), Sort.Order.desc("recruitRegDate") );
        else if(orderBy.equals("like")) sort = Sort.by( Sort.Order.desc("recruitBookmarked"), Sort.Order.desc("recruitRegDate") );
        else if(orderBy.equals("hit")) sort = Sort.by( Sort.Order.desc("recruitHit"), Sort.Order.desc("recruitRegDate") );
        else if(orderBy.equals("date")) sort = Sort.by( Sort.Order.desc("recruitRegDate") );

        return recruitRepository.findAll(spec, sort);
    }

    @Transactional
    public CreateAndDeleteRecruitResponse createRecruit(CreateRecruitRequest createRecruitRequest, MultipartFile file) {
        String recruitImagePath = null;
        try {
            recruitImagePath = awsS3Service.upload(file, "cb/recruit").getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Recruit newRecruit = new Recruit(
                createRecruitRequest.getUserId(),
                createRecruitRequest.getRecruitType(),
                createRecruitRequest.getRecruitMemberTotal(),
                createRecruitRequest.getRecruitTitle(),
                createRecruitRequest.getCategoryId(),
                createRecruitRequest.getRecruitPrice(),
                recruitImagePath);
        Recruit createdRecruit = recruitRepository.save(newRecruit);

        recruitMemberService.createRecruitMember(new UpdateRecruitRequest(
                createdRecruit.getRecruitUuid(),
                createdRecruit.getUserId()));

        notificationRepository.save( Notification.builder()
                                                 .userId(newRecruit.getUserId())
                                                 .notificationTitle("리크루트 등록 신청 완료")
                                                 .notificationContent("리크루트 '" + newRecruit.getRecruitTitle() + "'가 성공적으로 등록 신청되었습니다."
                                                         + "\n관리자가 확인 후, 등록될 예정입니다.")
                                                 .build());

        return createdRecruit.toCreateAndDeleteRecruitResponse();
    }

    @Transactional
    public void examineRecruit(UUID recruitUuid, ExamineRecruitRequest examineRecruitRequest) {
        recruitRepository.examineRecruit(recruitUuid,
                                         examineRecruitRequest.getExaminationResult(),
                                         examineRecruitRequest.getAdminId());

        Recruit recruit = recruitRepository.findById(recruitUuid).get();

        if(examineRecruitRequest.getExaminationResult().equals("A")) {
            notificationRepository.save( Notification.builder()
                    .userId(recruit.getUserId())
                    .notificationTitle("리크루트 등록 신청 통과")
                    .notificationContent("'" + recruit.getRecruitTitle() + "' 리크루트가 등록 처리되었습니다.")
                    .build());

        } else if(examineRecruitRequest.getExaminationResult().equals("D")) {
            notificationRepository.save( Notification.builder()
                    .userId(recruit.getUserId())
                    .notificationTitle("리크루트 등록 신청 반려")
                    .notificationContent("'" + recruit.getRecruitTitle() + "' 리크루트가 반려 처리되었습니다.")
                    .build());
        }

    }

    @Transactional
    public void participateRecruit(UpdateRecruitRequest participateRecruitRequest) {
        recruitMemberService.createRecruitMember(UpdateRecruitRequest.builder()
                                                                     .recruitUuid(participateRecruitRequest.getRecruitUuid())
                                                                     .userId(participateRecruitRequest.getUserId())
                                                                     .build());
        recruitRepository.participateRecruit(participateRecruitRequest.getRecruitUuid());
        bookmarkRepository.plusRecruitMemberParticipatedOfBookmark(participateRecruitRequest.getRecruitUuid());

        Recruit recruit = recruitRepository.findById(participateRecruitRequest.getRecruitUuid()).get();

        notificationRepository.save( Notification.builder()
                .userId(recruit.getUserId())
                .notificationTitle("'" + recruit.getRecruitTitle() + "' 리크루트 참여")
                .notificationContent("'" + recruit.getRecruitTitle() + "' 리크루트에 참여 처리되었습니다.")
                .build());

        // 리크루트 마감까지 2명 이하로 남았을 경우, 북마크한 사용자들에게 알림
        if((recruit.getRecruitMemberTotal() - recruit.getRecruitMemberParticipated()) <= 2) {

            List<String> bookmarkMember = bookmarkService.getBookmarkUserOfRecruit(recruit.getRecruitUuid());
            bookmarkMember.stream()
                    .forEach(i -> notificationRepository.save(
                            Notification.builder()
                                    .userId(i)
                                    .notificationTitle("'" + recruit.getRecruitTitle() + "' 리크루트 마감 임박")
                                    .notificationContent("'" + recruit.getRecruitTitle() + "' 리크루트 마감까지 " +
                                            + (recruit.getRecruitMemberTotal() - recruit.getRecruitMemberParticipated())
                                            + "명 남았습니다.")
                                    .build()) );
        // 리크루트 마감 처리
        } else if(recruit.getRecruitMemberParticipated() == recruit.getRecruitMemberTotal()) {

            recruitRepository.closeRecruit(participateRecruitRequest.getRecruitUuid());
            bookmarkRepository.deleteAllByRecruitUuid(participateRecruitRequest.getRecruitUuid());

            // 참여 마감 시, 참여 인원들에게 알림 처리
            List<String> recruitMember = recruitMemberService.getRecruitMemberByRecruitUuid(recruit.getRecruitUuid());
            recruitMember.stream()
                    .forEach(i -> notificationRepository.save(
                            Notification.builder()
                                    .userId(i)
                                    .notificationTitle("'" + recruit.getRecruitTitle() + "' 리크루트 마감")
                                    .notificationContent("'" + recruit.getRecruitTitle() + "' 리크루트가 마감되었습니다." +
                                            "\n참여 인원들과 함께 공동 구매 처리되었습니다.")
                                    .build()) );

            // 참여 마감 시, 북마크한 사용자들에게 알림 처리
            List<String> bookmarkMember = bookmarkService.getBookmarkUserOfRecruit(recruit.getRecruitUuid());
            bookmarkMember.stream()
                    .forEach(i -> notificationRepository.save(
                            Notification.builder()
                                    .userId(i)
                                    .notificationTitle("'" + recruit.getRecruitTitle() + "' 리크루트 마감")
                                    .notificationContent("'" + recruit.getRecruitTitle() + "' 리크루트가 마감되었습니다.")
                                    .build()) );

        }





    }

    @Transactional
    public void cancelParticipateRecruit(UpdateRecruitRequest cancelParticipateRequest) {
        recruitRepository.cancelParticipateRecruit(cancelParticipateRequest.getRecruitUuid());
        recruitMemberService.deleteRecruitMember(cancelParticipateRequest);
        bookmarkRepository.minusRecruitMemberParticipatedOfBookmark(cancelParticipateRequest.getRecruitUuid());

        Recruit recruit = recruitRepository.findById(cancelParticipateRequest.getRecruitUuid()).get();

        notificationRepository.save( Notification.builder()
                .userId(recruit.getUserId())
                .notificationTitle("'" + recruit.getRecruitTitle() + "' 리크루트 참여 취소")
                .notificationContent("'" + recruit.getRecruitTitle() + "' 리크루트 참여 취소 처리되었습니다.")
                .build());
    }

    @Transactional
    public void closeRecruit(UpdateRecruitRequest closeRecruitRequest) {
        recruitRepository.closeRecruit(closeRecruitRequest.getRecruitUuid());
        bookmarkRepository.deleteAllByRecruitUuid(closeRecruitRequest.getRecruitUuid());

        Recruit recruit = recruitRepository.findById(closeRecruitRequest.getRecruitUuid()).get();

        notificationRepository.save( Notification.builder()
                .userId(recruit.getUserId())
                .notificationTitle("'" + recruit.getRecruitTitle() + "'리크루트 철회 완료")
                .notificationContent("리크루트 '" + recruit.getRecruitTitle() + "'가 철회 처리되었습니다.")
                .build());
    }

}
