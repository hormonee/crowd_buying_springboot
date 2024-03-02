package com.hormonic.crowd_buying.controller;

import com.hormonic.crowd_buying.domain.dto.request.SaveUserRequest;
import com.hormonic.crowd_buying.domain.dto.response.SaveUserResponse;
import com.hormonic.crowd_buying.domain.entity.User;
import com.hormonic.crowd_buying.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ArrayList<User> getUserList() {
        System.out.println("Controller 진입");
        ArrayList<User> userList = userService.getUserList();
        System.out.println(userList);
        return userList;
    }

    @GetMapping("/{name}")
    public User getUserByName(@PathVariable("name") String userName) {
        System.out.println("Controller 진입");
        System.out.println(userName);
        return userService.getUserByName(userName);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable("email") String userEmail) {
        return userService.getUserByEmail(userEmail);
    }

    @GetMapping("/filter/{component}")
    public List<User> getUserNameByComponent(@PathVariable("component") String component) {
        return userService.getUserListByComponent(component);
    }

    @GetMapping("/custom/{element}")
    public User testCustomizedQueryMethod(@PathVariable("element") String element) {
        return userService.testCustomizedMethod(element);
    }

    @GetMapping("/custom2/{element}")
    public List<User> testCustomizedQueryMethod2(@PathVariable("element") String element) {
        return userService.testCustomizedMethod2(element);
    }

    @PostMapping
    public ResponseEntity<SaveUserResponse> create(@RequestBody @Valid SaveUserRequest saveUserRequest) {
        return ResponseEntity.ok(userService.saveUser(saveUserRequest));
    }

}
