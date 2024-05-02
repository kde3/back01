package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.dto.LogInDTO;
import com.fiveis.leasemates.domain.vo.UserVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;
    UserVO userVO = new UserVO();

    @BeforeEach
    void setUp() {
        userVO.setId("test1");
        userVO.setName("test입니다");
        userVO.setPassword("1234");
        userVO.setEmail("test@gmail.com");
        userVO.setPhoneNumber("010-1111-1111");
    }

    @Test
    void join() {
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