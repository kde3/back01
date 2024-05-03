package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.dto.LogInDTO;
import com.fiveis.leasemates.domain.vo.UserVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    void join() {
        UserVO userVO = UserVO.builder()
                .id("test1")
                .name("test입니다")
                .password("1234")
                .email("hong@naver.com")
                .phoneNumber("010-1111-2222")
                .createdAt(LocalDate.now()).build();

        Boolean join = userService.join(userVO);
        System.out.println("join = " + join);
    }

    @Test
    void logIn() {
        LogInDTO logInDTO = new LogInDTO();
        logInDTO.setId("test1");
        logInDTO.setPassword("1234");
        Boolean login = userService.logIn(logInDTO);

        System.out.println("login = " + login);
    }
}