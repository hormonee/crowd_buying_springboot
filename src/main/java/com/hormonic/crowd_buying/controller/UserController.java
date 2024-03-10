package com.hormonic.crowd_buying.controller;

import com.hormonic.crowd_buying.domain.dto.request.user.CreateUserRequest;
import com.hormonic.crowd_buying.domain.dto.request.user.DeleteUserRequest;
import com.hormonic.crowd_buying.domain.dto.request.user.UpdateUserRequest;
import com.hormonic.crowd_buying.domain.dto.response.user.CreateAndDeleteUserResponse;
import com.hormonic.crowd_buying.domain.entity.User;
import com.hormonic.crowd_buying.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
public class UserController {
    private final UserService userService;

    @GetMapping
    @Operation(summary = "사용자 목록 조회", description = "관리자용 전체 사용자 목록 조회")
    public ResponseEntity<List<User>> getUserList() {
        // session accessType attribute 를 통해 관리자인지 검증 필요
        return ResponseEntity.ok(userService.getUserList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 사용자 정보 조회", description = "사용자 ID를 통해 특정 한 명의 사용자 정보 조회")
    public ResponseEntity<User> getUserByUserId(@PathVariable("id") String userId) {
        return ResponseEntity.ok(userService.getUserByUserId(userId));
    }

    @PostMapping
    @Operation(summary = "사용자 회원가입", description = "사용자 세부 정보를 입력 받아 회원가입 처리")
    @Parameters(value = {
            @Parameter(name = "userId", description = "아이디", required = true),
            @Parameter(name = "userPw", description = "비밀번호", required = true),
            @Parameter(name = "userName", description = "성명", required = true),
            @Parameter(name = "userBirth", description = "생년월일", required = true),
            @Parameter(name = "userContact", description = "연락처", required = true),
            @Parameter(name = "userAddress", description = "주소", required = true),
            @Parameter(name = "userEmail", description = "이메일", required = true),
            @Parameter(name = "userGender", description = "성별", required = true)
    })
    public ResponseEntity<CreateAndDeleteUserResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        return new ResponseEntity(userService.createUser(createUserRequest), HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "회원 정보 수정", description = "회원 정보 수정 처리")
    @Parameters(value = {
            @Parameter(name = "userId", description = "아이디", required = true),
            @Parameter(name = "userPw", description = "비밀번호", required = true),
            @Parameter(name = "userContact", description = "연락처", required = true),
            @Parameter(name = "userAddress", description = "주소", required = true),
            @Parameter(name = "userEmail", description = "이메일", required = true)
    })
    public ResponseEntity<User> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        userService.updateUser(updateUserRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 통한 회원 정보 삭제 대기 처리")
    public ResponseEntity<CreateAndDeleteUserResponse> deleteUser(@RequestBody DeleteUserRequest deleteUserRequest){
        return ResponseEntity.ok(userService.deleteUser(deleteUserRequest.getUserId()));
    }

}
