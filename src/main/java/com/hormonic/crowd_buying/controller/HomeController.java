package com.hormonic.crowd_buying.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String temp() {
        return "temp";
    }

    @GetMapping("/awsS3Test")
    public void awsS3Test(Model model) {
    }

}
