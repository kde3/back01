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

    /**
     * 페이지 전달
     */
    @GetMapping("/login")
    String logInView() {
        return "login/login";
    }

    @GetMapping("/join")
    String joinView() {
        return "login/regist";
    }


    /**
     * 회원가입 기능
     */
    @ResponseBody
    @PostMapping("/join")
    ResponseEntity<Map> join(@RequestBody UserVO userVO) {
        Map<String, String> response = new HashMap<>();

        System.out.println("userVO : " + userVO);

        Boolean result = userService.join(userVO);


        if(result) {
            response.put("message", "회원가입 성공");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        response.put("message", "회원가입 실패");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }


    /**
     * 로그인 기능
     */
    @ResponseBody
    @PostMapping("/login")
    ResponseEntity<Map> logIn(@RequestBody LogInDTO logInDTO) {
        Map<String, String> response = new HashMap<>();

        Boolean result = userService.logIn(logInDTO);
        if(result) {
            response.put("message", "로그인 성공");
            return ResponseEntity.ok(response);
        }

        response.put("message", "로그인 실패");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

}
