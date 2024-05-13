package com.fiveis.leasemates.controller;

import com.fiveis.leasemates.domain.dto.JoinDTO;
import com.fiveis.leasemates.domain.dto.LogInDTO;
import com.fiveis.leasemates.domain.vo.UserVO;
import com.fiveis.leasemates.security.CustomUserDetails;
import com.fiveis.leasemates.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    CustomUserDetails customUserDetails = (CustomUserDetails) principal;

    @GetMapping("/login")
    public void login() {}

    @GetMapping("/join")
    public void join() {}

    @GetMapping("/join_terms")
    public void joinTerms() {}

//    @GetMapping("/info")
//    String userInfoView() {
//        return "user/index";
//    }


    /**
     * 회원가입 기능
     */
    @PostMapping("/join")
    String join(JoinDTO joinDTO) {
        Boolean result = userService.join(joinDTO);

        if(result) {
            return "redirect:/user/login";
        }

        return "/user/join";
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
