package com.fiveis.leasemates.controller;

import com.fiveis.leasemates.domain.dto.LogInDTO;
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

    @GetMapping("/logIn")
    String logInView() {
        return "user/logIn";
    }

    @ResponseBody
    @PostMapping("/logIn")
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
