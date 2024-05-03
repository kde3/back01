package com.fiveis.leasemates.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * header와  footer를 보여주기 위함
 */

@Controller
@RequestMapping("/temp")
public class TempController {

    @GetMapping("/header/before_login")
    String headerBeforeLogIn() {
        return "/temp/header/before_login/header";
    }

    @GetMapping("/header/after_login")
    String headerAfterLogIn() {
        return "/temp/header/after_login/header";
    }

    @GetMapping("/footer")
    String footer() {
        return "/temp/footer";
    }
}
