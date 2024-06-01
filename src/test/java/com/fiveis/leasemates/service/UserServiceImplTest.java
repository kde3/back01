package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.vo.UserVO;
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
                .build();
    }
}