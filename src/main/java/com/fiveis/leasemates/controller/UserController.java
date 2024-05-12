package com.fiveis.leasemates.controller;

import com.fiveis.leasemates.domain.dto.LogInDTO;
import com.fiveis.leasemates.domain.vo.UserVO;
import com.fiveis.leasemates.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public void login() {}

    @GetMapping("/join")
    public void join() {}

//    @GetMapping("/info")
//    String userInfoView() {
//        return "user/index";
//    }


    /**
     * 회원가입 기능
     */
    @PostMapping("/join")
    String join(UserVO userVO) {
        Boolean result = userService.join(userVO);

        return "redirect:/";
    }

    /**
     * 로그아웃
     * @return
     */
    @PostMapping("/logout")
    public String logout() {
        return "redirect:/community/";
    }
}
