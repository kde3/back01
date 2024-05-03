package com.fiveis.leasemates.repository;

import com.fiveis.leasemates.domain.vo.UserVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("유저 생성")
    void createUser() {
        UserVO userVO = UserVO.builder()
                .userNo("uuidtest1")
                .id("hong12")
                .password("1234")
                .name("test다")
                .email("hong@naver.com")
                .phoneNumber("010-1111-2222")
                .createdAt(LocalDate.now()).build();

        userRepository.createUser(userVO);
    }

    @Test
    @DisplayName("유저 검색")
    void findById() {
        Optional<UserVO> userVO = userRepository.findById("uuidtest");
        System.out.println("user = " + userVO.get());
    }
}