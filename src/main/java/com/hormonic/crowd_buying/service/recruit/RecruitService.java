package com.hormonic.crowd_buying.service.recruit;

import com.hormonic.crowd_buying.advice.exception.AwsS3UploadFailException;
import com.hormonic.crowd_buying.advice.payload.ErrorCode;
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

@Service
@RequiredArgsConstructor
public class RecruitService {
    private final RecruitRepository recruitRepository;
    private final RecruitValidator recruitValidator;
    private final RecruitMemberService recruitMemberService;
    private final AwsS3Service awsS3Service;
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkService bookmarkService;
    private final NotificationRepository notificationRepository;

    // 리크루트 클릭 처리 - 세부 정보 조회 및 조회수 증가
    @Transactional
    public Optional<Recruit> getRecruitByRecruitUuid(UUID recruitUuid) {
        Optional<Recruit> recruit = recruitRepository.findById(recruitUuid);
        recruitValidator.validateIsAvailable(recruit.get().getRecruitEndDate());
        recruitRepository.clickRecruit(recruitUuid);

        return recruit;
    }

    // 사용자용 리크루트 목록 조회 - 등록 심사 통과하고 종료되지 않은 리크루트들만 출력
    public List<Recruit> getRecruitListForUser(GetRecruitListRequest getRecruitListRequest) {
        // Pageable pageable = PageRequest.ofSize(2).withPage(1).withSort(sort);
        Specification<Recruit> spec;

        // 카테고리 미지정
        if(getRecruitListRequest.getCategoryId() == null) {
            spec = JpaSpecification.isEndedRecruit("N")
                    .and( JpaSpecification.recruitTitleLike(getRecruitListRequest.getRecruitTitle())
                    .and( JpaSpecification.examinationResultLike("A") ) );
        // 카테고리 지정
        } else {
            spec = JpaSpecification.isEndedRecruit("N")
                    .and( JpaSpecification.recruitCategoryLike(getRecruitListRequest.getCategoryId())
                    .and( JpaSpecification.recruitTitleLike(getRecruitListRequest.getRecruitTitle())
                    .and( JpaSpecification.examinationResultLike("A") ) ) );
        }
        // 정렬 기준
        String orderBy = getRecruitListRequest.getOrderBy();
        Sort sort = null;
        if(orderBy == null) sort = Sort.by( Sort.Order.desc("recruitBookmarked"), Sort.Order.desc("recruitRegDate") );
        else if(orderBy.equals("hit")) sort = Sort.by( Sort.Order.desc("recruitHit"), Sort.Order.desc("recruitRegDate") );
        else if(orderBy.equals("date")) sort = Sort.by( Sort.Order.desc("recruitRegDate") );
        else if(orderBy.equals("bookmark")) sort = Sort.by( Sort.Order.desc("recruitBookmarked"), Sort.Order.desc("recruitRegDate") );

        return recruitRepository.findAll(spec, sort);
    }

    // 관리자용 리크루트 목록 조회 - 모든 리크루트 조회 가능
    public List<Recruit> getRecruitListForAdmin(GetRecruitListRequest getRecruitListRequest) {
        Specification<Recruit> spec = null;

        // 카테고리 미지정
        if(getRecruitListRequest.getCategoryId() == null) {
            // 종료 여부 미지정
            if(getRecruitListRequest.getIsEnded().equals("A")) {
                spec = JpaSpecification.recruitTitleLike(getRecruitListRequest.getRecruitTitle())
                        .and( JpaSpecification.examinationResultLike("%" + getRecruitListRequest.getExaminationResult() + "%") );
           // 종료 여부 지정
            } else {
                spec = JpaSpecification.isEndedRecruit(getRecruitListRequest.getIsEnded())
                        .and( JpaSpecification.recruitTitleLike(getRecruitListRequest.getRecruitTitle())
                        .and( JpaSpecification.examinationResultLike("%" + getRecruitListRequest.getExaminationResult() + "%") ) );
            }

        // 카테고리 지정
        } else {
            // 종료 여부 미지정
            if(getRecruitListRequest.getIsEnded().equals("A")) {
                JpaSpecification.recruitCategoryLike(getRecruitListRequest.getCategoryId())
                        .and( JpaSpecification.recruitTitleLike(getRecruitListRequest.getRecruitTitle())
                        .and( JpaSpecification.examinationResultLike("%" + getRecruitListRequest.getExaminationResult() + "%") ) );
            // 종료 여부 지정
            } else {
                spec = JpaSpecification.isEndedRecruit(getRecruitListRequest.getIsEnded())
                        .and( JpaSpecification.recruitCategoryLike(getRecruitListRequest.getCategoryId())
                        .and( JpaSpecification.recruitTitleLike(getRecruitListRequest.getRecruitTitle())
                        .and( JpaSpecification.examinationResultLike("%" + getRecruitListRequest.getExaminationResult() + "%") ) ) );
            }
        }
        // 정렬 기준
        String orderBy = getRecruitListRequest.getOrderBy();
        Sort sort = null;
        if(orderBy == null) sort = Sort.by( Sort.Order.desc("recruitRegDate") );
        else if(orderBy.equals("bookmark")) sort = Sort.by( Sort.Order.desc("recruitBookmarked"), Sort.Order.desc("recruitRegDate") );
        else if(orderBy.equals("hit")) sort = Sort.by( Sort.Order.desc("recruitHit"), Sort.Order.desc("recruitRegDate") );
        else if(orderBy.equals("date")) sort = Sort.by( Sort.Order.desc("recruitRegDate") );

        return recruitRepository.findAll(spec, sort);
    }

    // 리크루트 생성 처리 -
    @Transactional
    public CreateAndDeleteRecruitResponse createRecruit(CreateRecruitRequest createRecruitRequest, MultipartFile file) {
        // 리크루트 분담금이 5만원 미만인 경우 Exception 발생
        recruitValidator.validateAllotment(createRecruitRequest);

        // AWS S3에 이미지 업로드하고 path값 저장
        String recruitImagePath = null;
        try {
            recruitImagePath = awsS3Service.upload(file, "cb/recruit").getPath();

        } catch (AwsS3UploadFailException e) {
            new AwsS3UploadFailException(ErrorCode.S3_UPLOAD_FAIL).getMessage();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 리크루트 생성
        Recruit newRecruit = new Recruit(
                createRecruitRequest.getUserId(),
                createRecruitRequest.getRecruitType(),
                createRecruitRequest.getRecruitMemberTotal(),
                createRecruitRequest.getRecruitTitle(),
                createRecruitRequest.getCategoryId(),
                createRecruitRequest.getRecruitPrice(),
                recruitImagePath);
        Recruit createdRecruit = recruitRepository.save(newRecruit);

        // 리크루트 멤버 등록
        recruitMemberService.createRecruitMember(new UpdateRecruitRequest(
                createdRecruit.getRecruitUuid(),
                createdRecruit.getUserId()));

        // 리크루트 등록 신청 완료 알림
        notificationRepository.save( Notification.builder()
                                                 .userId(newRecruit.getUserId())
                                                 .notificationTitle("리크루트 등록 신청 완료")
                                                 .notificationContent("리크루트 '" + newRecruit.getRecruitTitle() + "'가 성공적으로 등록 신청되었습니다."
                                                         + "\n관리자가 확인 후, 등록될 예정입니다.")
                                                 .build());

        return createdRecruit.toCreateAndDeleteRecruitResponse();
    }

    // 리크루트 등록 신청 심사 처리
    @Transactional
    public void examineRecruit(UUID recruitUuid, ExamineRecruitRequest examineRecruitRequest) {
        // 리크루트 examinationResult 값 업데이트
        recruitRepository.examineRecruit(recruitUuid,
                                         examineRecruitRequest.getExaminationResult(),
                                         examineRecruitRequest.getAdminId());

        Recruit recruit = recruitRepository.findById(recruitUuid).get();
        // 등록 심사 통과 처리 알림
        if(examineRecruitRequest.getExaminationResult().equals("A")) {
            notificationRepository.save( Notification.builder()
                    .userId(recruit.getUserId())
                    .notificationTitle("리크루트 등록 신청 통과")
                    .notificationContent("'" + recruit.getRecruitTitle() + "' 리크루트가 등록 처리되었습니다.")
                    .build());
        // 등록 심사 반려 처리 알림
        } else if(examineRecruitRequest.getExaminationResult().equals("D")) {
            notificationRepository.save( Notification.builder()
                    .userId(recruit.getUserId())
                    .notificationTitle("리크루트 등록 신청 반려")
                    .notificationContent("'" + recruit.getRecruitTitle() + "' 리크루트가 반려 처리되었습니다.")
                    .build());
        }

    }

    // 리크루트 참여 처리
    @Transactional
    public void participateRecruit(UpdateRecruitRequest participateRecruitRequest) {
        // 기존에 리크루트 멤버가 아닌 것이 맞는지 검증
        recruitValidator.validateIsMemberOfRecruit(recruitMemberService.isParticipatingRecruit(participateRecruitRequest));

        // 리크루트 멤버 등록, 리크루트 참여 인원 수 업데이트, 북마크 엔티티 내 참여 인원 수 업데이트
        recruitMemberService.createRecruitMember(UpdateRecruitRequest.builder()
                                                                     .recruitUuid(participateRecruitRequest.getRecruitUuid())
                                                                     .userId(participateRecruitRequest.getUserId())
                                                                     .build());
        recruitRepository.participateRecruit(participateRecruitRequest.getRecruitUuid());
        bookmarkRepository.plusRecruitMemberParticipatedOfBookmark(participateRecruitRequest.getRecruitUuid());

        Recruit recruit = recruitRepository.findById(participateRecruitRequest.getRecruitUuid()).get();
        // 신규 참여자에게 알림
        notificationRepository.save( Notification.builder()
                                                 .userId(participateRecruitRequest.getUserId())
                                                 .notificationTitle("'" + recruit.getRecruitTitle() + "' 리크루트 참여")
                                                 .notificationContent("'" + recruit.getRecruitTitle() + "' 리크루트에 참여 처리되었습니다.")
                                                 .build());

        // 리크루트 마감 처리
        if(recruit.getRecruitMemberParticipated() == recruit.getRecruitMemberTotal()) {
            // 대표 소유자 선정
            List<String> recruitMember = recruitMemberService.getRecruitMemberByRecruitUuid(recruit.getRecruitUuid());
            String representativeParticipantId = recruitMember.get((int) (Math.random() * 5));

            // 참여 인원들에게 리크루트 마감 및 대표 구매자 정보 알림 처리
            recruitMember.stream()
                    .forEach(i -> notificationRepository.save(
                            Notification.builder()
                                        .userId(i)
                                        .notificationTitle("'" + recruit.getRecruitTitle() + "' 리크루트 마감")
                                        .notificationContent("'" + recruit.getRecruitTitle() + "' 리크루트가 마감되었습니다." +
                                                "\n참여 인원들과 함께 공동 구매 처리되었습니다." +
                                                "\n대표 구매자 ID: " + representativeParticipantId)
                                        .build()) );

            // 북마크한 사용자들에게 리크루트 마감 알림 처리
            List<String> bookmarkMember = bookmarkService.getBookmarkUserOfRecruit(recruit.getRecruitUuid());
            bookmarkMember.stream()
                    .forEach(i -> notificationRepository.save(
                            Notification.builder()
                                        .userId(i)
                                        .notificationTitle("'" + recruit.getRecruitTitle() + "' 리크루트 마감")
                                        .notificationContent("'" + recruit.getRecruitTitle() + "' 리크루트가 마감되었습니다.")
                                        .build()) );

            // 리크루트 종료 처리 및 관련 북마크 정보 제거
            recruitRepository.closeRecruit(recruit.getRecruitUuid());
            bookmarkRepository.deleteAllByRecruitUuid(recruit.getRecruitUuid());

        // 리크루트 마감까지 2명 이하로 남았을 경우, 북마크한 사용자들에게 알림
        } else if((recruit.getRecruitMemberTotal() - recruit.getRecruitMemberParticipated()) <= 2) {
            List<String> bookmarkMember = bookmarkService.getBookmarkUserOfRecruit(recruit.getRecruitUuid());
            bookmarkMember.stream()
                    .forEach(i -> notificationRepository.save(
                            Notification.builder()
                                        .userId(i)
                                        .notificationTitle("'" + recruit.getRecruitTitle() + "' 리크루트 마감 임박")
                                        .notificationContent("'" + recruit.getRecruitTitle() + "' 리크루트 마감까지 " +
                                                +(recruit.getRecruitMemberTotal() - recruit.getRecruitMemberParticipated())
                                                + "명 남았습니다.")
                                        .build()));
        }
    }

    //
    @Transactional
    public void cancelParticipateRecruit(UpdateRecruitRequest cancelParticipateRequest) {
        // 기존에 리크루트 멤버가 맞는지 검증
        recruitValidator.validateIsNotMemberOfRecruit(recruitMemberService.isParticipatingRecruit(cancelParticipateRequest));

        Recruit recruit = recruitRepository.findById(cancelParticipateRequest.getRecruitUuid()).get();
        // 참여 인원이 0명이 되어 리크루트 종료 처리
        if(recruit.getRecruitMemberParticipated() == 1) {
            // 참여 취소자에게 알림
            notificationRepository.save( Notification.builder()
                    .userId(cancelParticipateRequest.getUserId())
                    .notificationTitle("'" + recruit.getRecruitTitle() + "' 리크루트 참여 취소")
                    .notificationContent("'" + recruit.getRecruitTitle() + "' 리크루트 참여 취소 처리되었습니다.")
                    .build());

            // 리크루트 북마크한 사용자들에게 종료 알림
            List<String> bookmarkMember = bookmarkService.getBookmarkUserOfRecruit(cancelParticipateRequest.getRecruitUuid());
            bookmarkMember.stream()
                    .forEach(i -> notificationRepository.save(
                            Notification.builder()
                                    .userId(i)
                                    .notificationTitle("'" + recruit.getRecruitTitle() + "' 리크루트 종료")
                                    .notificationContent("'" + recruit.getRecruitTitle() + "' 리크루트가 종료되었습니다.")
                                    .build()) );

            // 리크루트 종료 처리로 인한 Recruit, RecruitMember, Bookmark 엔티티 수정 처리
            recruitRepository.closeRecruit(recruit.getRecruitUuid());
            recruitMemberService.deleteRecruitMember(cancelParticipateRequest);
            bookmarkRepository.deleteAllByRecruitUuid(recruit.getRecruitUuid());

        // 일반적인 참여 취소 처리
        } else {
            // 리크루트 참여 취소 처리로 인한 Recruit, RecruitMember, Bookmark 엔티티 수정 처리
            recruitRepository.cancelParticipateRecruit(recruit.getRecruitUuid());
            recruitMemberService.deleteRecruitMember(cancelParticipateRequest);
            bookmarkRepository.minusRecruitMemberParticipatedOfBookmark(recruit.getRecruitUuid());

            // 참여 취소자에게 알림
            notificationRepository.save( Notification.builder()
                    .userId(cancelParticipateRequest.getUserId())
                    .notificationTitle("'" + recruit.getRecruitTitle() + "' 리크루트 참여 취소")
                    .notificationContent("'" + recruit.getRecruitTitle() + "' 리크루트 참여 취소 처리되었습니다.")
                    .build());
        }
    }

    @Transactional
    public void closeRecruit(UpdateRecruitRequest closeRecruitRequest) {
        Recruit recruit = recruitRepository.findById(closeRecruitRequest.getRecruitUuid()).get();

        // 리크루트 참여 인원들에게 철회 알림 보내기
        List<String> recruitMember = recruitMemberService.getRecruitMemberByRecruitUuid(closeRecruitRequest.getRecruitUuid());
        recruitMember.stream()
                .forEach(i -> notificationRepository.save(
                        Notification.builder()
                                    .userId(i)
                                    .notificationTitle("'" + recruit.getRecruitTitle() + "' 리크루트 철회")
                                    .notificationContent("'" + recruit.getRecruitTitle() + "' 리크루트가 관리자에 의해 철회되었습니다.")
                                    .build()) );

        // 북마크한 사용자들에게 리크루트 철회 알림 보내기
        List<String> bookmarkMember = bookmarkService.getBookmarkUserOfRecruit(closeRecruitRequest.getRecruitUuid());
        bookmarkMember.stream()
                .forEach(i -> notificationRepository.save(
                        Notification.builder()
                                    .userId(i)
                                    .notificationTitle("'" + recruit.getRecruitTitle() + "' 리크루트 철회")
                                    .notificationContent("'" + recruit.getRecruitTitle() + "' 리크루트가 관리자에 의해 철회되었습니다.")
                                    .build()) );

        // 리크루트 철회 처리, RecruitMember 삭제 및 철회된 리크루트 관련 북마크 제거
        recruitRepository.closeRecruit(closeRecruitRequest.getRecruitUuid());
        recruitMemberService.deleteAllRecruitMemberByRecruitUuid(closeRecruitRequest.getRecruitUuid());
        bookmarkRepository.deleteAllByRecruitUuid(closeRecruitRequest.getRecruitUuid());
    }

}
