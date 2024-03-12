package com.hormonic.crowd_buying.controller;

import com.hormonic.crowd_buying.service.admin.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Administrator", description = "Administrator API")
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    public String adminHome() {
        return "Admin Controller";
    }

}
