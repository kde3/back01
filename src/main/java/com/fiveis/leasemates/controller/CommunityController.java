package com.fiveis.leasemates.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommunityController {

    @GetMapping("/")
    public String indexView() {
        return "index";
    }


}
