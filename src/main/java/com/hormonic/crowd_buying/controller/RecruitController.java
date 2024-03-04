package com.hormonic.crowd_buying.controller;

import com.hormonic.crowd_buying.domain.entity.User;
import com.hormonic.crowd_buying.service.recruit.RecruitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recruits")
@RequiredArgsConstructor
public class RecruitController {
    private final RecruitService recruiteService;

//    @GetMapping
//    public ResponseEntity<List<User>> getUserList() {
//        return ResponseEntity.ok(recruiteService.getRecruitList());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserByUserId(@PathVariable("id") String userId) {
//        return ResponseEntity.ok(recruiteService.getRecruitByUserId(userId));
//    }

}
