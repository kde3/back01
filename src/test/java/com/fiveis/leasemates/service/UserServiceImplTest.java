package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.dto.user.JoinDTO;
import com.fiveis.leasemates.domain.vo.UserVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    @DisplayName("회원가입")
    void join() {
        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setId("test");
        joinDTO.setName("test");
        joinDTO.setPassword("1234");
        joinDTO.setEmail("test@naver.com");
        joinDTO.setPhoneNumber("010-1111-1111");

        Boolean join = userService.join(joinDTO);
    }
}