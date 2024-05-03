package com.fiveis.leasemates.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/community")
public class CommunityController {

    @GetMapping("/retrieve")
    public String mainBeforeLoginView() {
        return "commu/index/before/before";
    }




}
