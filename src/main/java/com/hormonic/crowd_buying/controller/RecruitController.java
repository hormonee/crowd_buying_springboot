package com.hormonic.crowd_buying.controller;

import com.hormonic.crowd_buying.domain.dto.request.recruit.CreateRecruitRequest;
import com.hormonic.crowd_buying.domain.dto.request.recruit.ExamineRecruitRequest;
import com.hormonic.crowd_buying.domain.dto.request.recruit.GetRecruitListRequest;
import com.hormonic.crowd_buying.domain.dto.request.recruit.UpdateRecruitRequest;
import com.hormonic.crowd_buying.domain.dto.response.recruit.CreateAndDeleteRecruitResponse;
import com.hormonic.crowd_buying.domain.entity.recruit.Recruit;
import com.hormonic.crowd_buying.service.recruit.RecruitMemberService;
import com.hormonic.crowd_buying.service.recruit.RecruitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/recruits")
@RequiredArgsConstructor
public class RecruitController {
    private final RecruitService recruitService;
    private final RecruitMemberService recruitMemberService;

    @GetMapping
    public ResponseEntity<List<Recruit>> getRecruitList(@RequestBody GetRecruitListRequest getRecruitListRequest) {
        return ResponseEntity.ok(recruitService.getRecruitList(getRecruitListRequest));
    }

//    @GetMapping("/{uuid}")
//    public ResponseEntity<Optional<Recruit>> getRecruitByRecruitUuid(@PathVariable("uuid") UUID recruitUuid) {
//        return ResponseEntity.ok(recruitService.getRecruitByRecruitUuid(recruitUuid));
//    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Map<String, Object>> getRecruitByRecruitUuid(@PathVariable("uuid") UUID recruitUuid) {
        Map<String, Object> recruitInfo = new HashMap();

        Recruit recruit = recruitService.getRecruitByRecruitUuid(recruitUuid).get();
        recruitInfo.put("recruit", recruit);

        List<String> recruitMember = recruitMemberService.getRecruitMemberByRecruitUuidConsideringRecruitType((recruitUuid));
        recruitInfo.put("recruitMember", recruitMember);

        return ResponseEntity.ok(recruitInfo);
    }

    @PostMapping
    public ResponseEntity<CreateAndDeleteRecruitResponse> createRecruit(@RequestPart("dto") @Valid CreateRecruitRequest createRecruitRequest,
                                                                        @RequestPart("file") MultipartFile file) {
        return new ResponseEntity(recruitService.createRecruit(createRecruitRequest, file), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}/examine")
    public ResponseEntity<Recruit> examineRecruit(@PathVariable("uuid") UUID recruitUuid,
                              @RequestBody @Valid ExamineRecruitRequest examineRecruitRequest) {
        recruitService.examineRecruit(recruitUuid, examineRecruitRequest);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{uuid}/participate")
    public ResponseEntity<Recruit> participateRecruit(@PathVariable("uuid") UUID recruitUuid,
                                                      @RequestParam(value = "userId") String userId) {
        recruitService.participateRecruit(new UpdateRecruitRequest(recruitUuid, userId));
        return ResponseEntity.ok().build();  // @RequestBody 로 바꾸지 않아도 가능한지?
    }

    @PutMapping("/{uuid}/withdraw")
    public ResponseEntity<Recruit> cancelParticipateRecruit(@PathVariable("uuid") UUID recruitUuid,
                                                            @RequestParam(value = "userId") String userId) {
        recruitService.cancelParticipateRecruit(new UpdateRecruitRequest(recruitUuid, userId));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{uuid}/close")
    public ResponseEntity<Recruit> closeRecruit(@PathVariable("uuid") UUID recruitUuid) {
        recruitService.closeRecruit(new UpdateRecruitRequest(recruitUuid));
        return ResponseEntity.ok().build();
    }

}
