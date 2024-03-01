package com.hormonic.crowd_buying.controller;

import com.hormonic.crowd_buying.domain.entity.User;
import com.hormonic.crowd_buying.service.user.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @GetMapping("/name/{name}")
    public User getUserByName(@PathVariable("name") String userName) {
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

    @PostMapping()
    public User createUser() {
        User newUser = User.builder()
                .userName("kim2")
                .userEmail("kim2@gmail.com")
                .build();
        return userService.createUser(newUser);
    }

}
