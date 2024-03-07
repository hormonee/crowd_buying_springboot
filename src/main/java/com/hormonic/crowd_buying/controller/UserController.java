package com.hormonic.crowd_buying.controller;

import com.hormonic.crowd_buying.domain.dto.request.user.CreateUserRequest;
import com.hormonic.crowd_buying.domain.dto.request.user.DeleteUserRequest;
import com.hormonic.crowd_buying.domain.dto.request.user.UpdateUserRequest;
import com.hormonic.crowd_buying.domain.dto.response.user.CreateAndDeleteUserResponse;
import com.hormonic.crowd_buying.domain.entity.User;
import com.hormonic.crowd_buying.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUserList() {
        return ResponseEntity.ok(userService.getUserList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByUserId(@PathVariable("id") String userId) {
        return ResponseEntity.ok(userService.getUserByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<CreateAndDeleteUserResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        return new ResponseEntity(userService.createUser(createUserRequest), HttpStatus.CREATED);
    }

    @PutMapping
    public int updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        if(userService.updateUser(updateUserRequest) == 1) return 1;
        return 0;
    }

    @DeleteMapping
    public ResponseEntity<CreateAndDeleteUserResponse> deleteUser(@RequestBody DeleteUserRequest deleteUserRequest){
        return ResponseEntity.ok(userService.deleteUser(deleteUserRequest.getUserId()));
    }

}
