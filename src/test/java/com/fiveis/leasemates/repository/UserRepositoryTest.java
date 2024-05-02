package com.fiveis.leasemates.repository;

import com.fiveis.leasemates.domain.vo.UserVO;
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
    void findById() {
        Optional<UserVO> userVO = userRepository.findById("hong12");
        System.out.println("user = " + userVO.get());
    }

    @Test
    void createUser() {
        UserVO userVO = new UserVO(
                "uuidtest",
                "hong12",
                "1234",
                "홍길동입니다",
                "hong@naver.com",
                "010-1111-2222",
                LocalDate.now()
        );

        userRepository.createUser(userVO);
    }
}