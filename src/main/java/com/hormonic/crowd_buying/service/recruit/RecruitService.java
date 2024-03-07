package com.hormonic.crowd_buying.service.recruit;

import com.hormonic.crowd_buying.domain.dto.request.recruit.CreateRecruitRequest;
import com.hormonic.crowd_buying.domain.dto.request.recruit.ExamineRecruitRequest;
import com.hormonic.crowd_buying.domain.dto.request.recruit.GetRecruitRequest;
import com.hormonic.crowd_buying.domain.dto.request.recruit.UpdateRecruitRequest;
import com.hormonic.crowd_buying.domain.dto.response.recruit.CreateAndDeleteRecruitResponse;
import com.hormonic.crowd_buying.domain.entity.recruit.Recruit;
import com.hormonic.crowd_buying.util.specification.JpaSpecification;
import com.hormonic.crowd_buying.repository.BookmarkRepository;
import com.hormonic.crowd_buying.repository.RecruitRepository;
import com.hormonic.crowd_buying.service.aws.AwsS3Service;
import com.hormonic.crowd_buying.service.bookmark.BookmarkService;
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
    private final RecruitMapper recruitMapper;
    private final RecruitRepository recruitRepository;
    private final RecruitMemberService recruitMemberService;
    private final AwsS3Service awsS3Service;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public Optional<Recruit> getRecruitByRecruitUuid(UUID recruitUuid) {
        recruitRepository.clickRecruit(recruitUuid);
        return recruitRepository.findById(recruitUuid);
    }

    public List<Recruit> getRecruitList(GetRecruitRequest getRecruitRequest) {
        // Pageable pageable = PageRequest.ofSize(2).withPage(1).withSort(sort);
        Specification<Recruit> spec;
        if(getRecruitRequest.getCategoryId() == 0) {
            spec = JpaSpecification.notEndedRecruit()
                    .and( JpaSpecification.recruitTitleLike(getRecruitRequest.getRecruitTitle()) );
        } else {
            spec = JpaSpecification.notEndedRecruit()
                    .and( JpaSpecification.recruitCategoryLike(getRecruitRequest.getCategoryId())
                    .and( JpaSpecification.recruitTitleLike(getRecruitRequest.getRecruitTitle()) ));
        }

        String orderBy = getRecruitRequest.getOrderBy();
        Sort sort = null;
        if(orderBy == null) sort = Sort.by( Sort.Order.desc("recruitBookmarked"), Sort.Order.desc("recruitRegDate") );
        else if(orderBy.equals("like")) sort = Sort.by( Sort.Order.desc("recruitBookmarked"), Sort.Order.desc("recruitRegDate") );
        else if(orderBy.equals("hit")) sort = Sort.by( Sort.Order.desc("recruitHit"), Sort.Order.desc("recruitRegDate") );
        else if(orderBy.equals("date")) sort = Sort.by( Sort.Order.desc("recruitRegDate") );

        return recruitRepository.findAll(spec, sort);
    }

    /*// MyBatis Method
    public ArrayList<Recruit> getRecruitList(GetRecruitRequest getRecruitRequest) {
        return recruitMapper.getRecruitList(getRecruitRequest);
    }*/

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

        return createdRecruit.toCreateAndDeleteRecruitResponse();
    }

    @Transactional
    public void examineRecruit(UUID recruitUuid, ExamineRecruitRequest examineRecruitRequest) {
        recruitRepository.examineRecruit(recruitUuid,
                                         examineRecruitRequest.getExaminationResult(),
                                         examineRecruitRequest.getAdminId());
    }

    @Transactional
    public void participateRecruit(UpdateRecruitRequest participateRecruitRequest) {
        recruitMemberService.createRecruitMember(UpdateRecruitRequest.builder()
                                                                     .recruitUuid(participateRecruitRequest.getRecruitUuid())
                                                                     .userId(participateRecruitRequest.getUserId())
                                                                     .build());
        recruitRepository.participateRecruit(participateRecruitRequest.getRecruitUuid());
        bookmarkRepository.plusRecruitMemberParticipatedOfBookmark(participateRecruitRequest.getRecruitUuid());

        if(recruitRepository.findById(participateRecruitRequest.getRecruitUuid()).get().getRecruitMemberParticipated() ==
                recruitRepository.findById(participateRecruitRequest.getRecruitUuid()).get().getRecruitMemberTotal()) {

            recruitRepository.closeRecruit(participateRecruitRequest.getRecruitUuid());
            bookmarkRepository.deleteAllByRecruitUuid(participateRecruitRequest.getRecruitUuid());
        }
    }

    @Transactional
    public void cancelParticipateRecruit(UpdateRecruitRequest cancelParticipateRequest) {
        recruitRepository.cancelParticipateRecruit(cancelParticipateRequest.getRecruitUuid());
        recruitMemberService.deleteRecruitMember(cancelParticipateRequest);
        bookmarkRepository.minusRecruitMemberParticipatedOfBookmark(cancelParticipateRequest.getRecruitUuid());
    }

    @Transactional
    public void closeRecruit(UpdateRecruitRequest closeRecruitRequest) {
        recruitRepository.closeRecruit(closeRecruitRequest.getRecruitUuid());
        bookmarkRepository.deleteAllByRecruitUuid(closeRecruitRequest.getRecruitUuid());
    }

}
