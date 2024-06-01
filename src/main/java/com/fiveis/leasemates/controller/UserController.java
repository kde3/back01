package com.fiveis.leasemates.controller;

import com.fiveis.leasemates.domain.dto.user.JoinDTO;
import com.fiveis.leasemates.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

//    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    CustomUserDetails customUserDetails = (CustomUserDetails) principal;

//    @GetMapping("/login")
//    public void login() {}

    @GetMapping("/login")
    public void login(@ModelAttribute String message, Model model) {
        model.addAttribute(message);
    }

    @GetMapping("/join")
    public void join() {}

    @GetMapping("/join_terms")
    public void joinTerms() {}

//    @GetMapping("/info")
//    String userInfoView() {
//        return "user/index";
//    }

    @GetMapping("/login/error")
    public String loginError(RedirectAttributes redirectAttrs) {
        redirectAttrs.addFlashAttribute("message", "아이디나 비밀번호가 잘못되었습니다.");

        log.info("들어왔다");

        return "redirect:/user/login";
    }


    /**
     * 회원가입
     */
    @PostMapping("/join")
    String join(JoinDTO joinDTO) {
        Boolean result = userService.join(joinDTO);

        if(result) return "redirect:/user/login";

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
