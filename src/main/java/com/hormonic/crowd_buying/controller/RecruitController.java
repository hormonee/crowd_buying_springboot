package com.hormonic.crowd_buying.controller;

import com.hormonic.crowd_buying.domain.dto.request.recruit.CreateRecruitRequest;
import com.hormonic.crowd_buying.domain.dto.request.recruit.ExamineRecruitRequest;
import com.hormonic.crowd_buying.domain.dto.request.recruit.GetRecruitListRequest;
import com.hormonic.crowd_buying.domain.dto.request.recruit.UpdateRecruitRequest;
import com.hormonic.crowd_buying.domain.dto.response.recruit.CreateAndDeleteRecruitResponse;
import com.hormonic.crowd_buying.domain.entity.recruit.Recruit;
import com.hormonic.crowd_buying.service.recruit.RecruitMemberService;
import com.hormonic.crowd_buying.service.recruit.RecruitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/recruits")
@RequiredArgsConstructor
@Tag(name = "Recruit", description = "Recruit API")
public class RecruitController {
    private final RecruitService recruitService;
    private final RecruitMemberService recruitMemberService;

    @GetMapping
    @Operation(summary = "사용자용 리크루트 목록 조회", description = "카테고리 ID 검색명, 정렬 기준값들을 받아 종료되지 않고 심사 통과된 유효한 리크루트 목록 조회")
    public ResponseEntity<Page<Recruit>> getRecruitListForUser(@ModelAttribute GetRecruitListRequest getRecruitListRequest,
                                                               @PageableDefault(size=1, sort="categoryId", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(recruitService.getRecruitListForUser(getRecruitListRequest, pageable));
    }

    @GetMapping("/admin")
    @Operation(summary = "관리자용 리크루트 목록 조회", description = "모든 리크루트 목록을 조회 가능하며 필터 적용을 통해 원하는 종류의 리스트 추출 가능")
    public ResponseEntity<Page<Recruit>> getRecruitListForAdmin(@ModelAttribute GetRecruitListRequest getRecruitListRequest,
                                                                @PageableDefault(size=2, sort="recruitHit", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(recruitService.getRecruitListForAdmin(getRecruitListRequest, pageable));
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "특정 리크루트 세부 정보 조회", description = "리크루트 UUID를 통해 해당 리크루트의 세부 정보 및 참여중인 인원 조회")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "요청에 성공하셨습니다.", content = @Content(mediaType = "application/json")),
                           @ApiResponse(responseCode = "403", description = "유효하지 않은 데이터에 대한 요청입니다.", content = @Content(mediaType = "application/json"))})
    public ResponseEntity<Map<String, Object>> getRecruitByRecruitUuid(@PathVariable("uuid") UUID recruitUuid) {
        Map<String, Object> recruitInfo = new HashMap();

        Recruit recruit = recruitService.getRecruitByRecruitUuid(recruitUuid).get();
        recruitInfo.put("recruit", recruit);

        List<String> recruitMember = recruitMemberService.getRecruitMemberByRecruitUuidConsideringRecruitType((recruitUuid));
        recruitInfo.put("recruitMember", recruitMember);

        return ResponseEntity.ok(recruitInfo);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "리크루트 등록 신청", description = "리크루트 세부 정보와 대표 이미지를 받아 등록 신청 처리")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "요청에 성공하셨습니다.", content = @Content(mediaType = "multipart/form-data")),
                           @ApiResponse(responseCode = "400", description = "분담금이 5만원 미만입니다.", content = @Content(mediaType = "multipart/form-data")),
                           @ApiResponse(responseCode = "503", description = "파일을 AWS S3에 업로드하는데 실패했습니다.", content = @Content(mediaType = "multipart/form-data"))})
    public ResponseEntity<CreateAndDeleteRecruitResponse> createRecruit(@RequestPart("dto") @Valid CreateRecruitRequest createRecruitRequest,
                                                                        @RequestPart("file") MultipartFile file) {
        return new ResponseEntity(recruitService.createRecruit(createRecruitRequest, file), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}/examine")
    @Operation(summary = "리크루트 등록 신청 심사 처리", description = "관리자가 등록 신청된 리크루트를 심사하여 결과를 반영")
    public ResponseEntity<Recruit> examineRecruit(@PathVariable("uuid") UUID recruitUuid,
                              @RequestBody @Valid ExamineRecruitRequest examineRecruitRequest) {
        recruitService.examineRecruit(recruitUuid, examineRecruitRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{uuid}/participate")
    @Operation(summary = "리크루트 참여", description = "사용자가 리크루트에 참여")
    public ResponseEntity<Recruit> participateRecruit(@PathVariable("uuid") UUID recruitUuid,
                                                      @RequestParam(value = "userId") String userId) {
        recruitService.participateRecruit(new UpdateRecruitRequest(recruitUuid, userId));
        return ResponseEntity.ok().build();  // @RequestBody 로 바꾸지 않아도 가능한지?
    }

    @PutMapping("/{uuid}/withdraw")
    @Operation(summary = "리크루트 참여 취소", description = "남은 인원 수에 따라 리크루트 참여 취소 또는 종료 처리 ")
    public ResponseEntity<Recruit> cancelParticipateRecruit(@PathVariable("uuid") UUID recruitUuid,
                                                            @RequestParam(value = "userId") String userId) {
        recruitService.cancelParticipateRecruit(new UpdateRecruitRequest(recruitUuid, userId));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{uuid}/close")
    @Operation(summary = "리크루트 철회", description = "관리자가 리크루트 철회 처리")
    public ResponseEntity<Recruit> closeRecruit(@PathVariable("uuid") UUID recruitUuid) {
        recruitService.closeRecruit(new UpdateRecruitRequest(recruitUuid));
        return ResponseEntity.ok().build();
    }

}
