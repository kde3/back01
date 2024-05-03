package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.dto.LogInDTO;
import com.fiveis.leasemates.domain.vo.UserVO;
import com.fiveis.leasemates.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * 회원가입
     */
    public Boolean join(UserVO userVO) {
        // 중복불가 필드 검사
        //아이디 조회해서 검색되면 탈락
        Optional<UserVO> foundUser = userRepository.findById(userVO.getId());

        if(foundUser.isPresent()) {
            return false;
        }

        //통과되었다면 객체에 담아 저장
        UUID uuid = UUID.randomUUID();

        UserVO newUser = UserVO.builder()
                .userNo(uuid.toString())
                .id(foundUser.get().getId())
                .name(foundUser.get().getName())
                .password(foundUser.get().getPassword())
                .email(foundUser.get().getEmail())
                .phoneNumber(foundUser.get().getPhoneNumber())
                .createdAt(LocalDate.now()).build();

        userRepository.createUser(userVO);

        return true;
    }


    /**
     * 로그인
     */
    public Boolean logIn(LogInDTO logInDTO) {
        // 아이디와 비번 받아오면 검사
        Optional<UserVO> foundUser = userRepository.findById(logInDTO.getId());

        if(foundUser.isPresent() && foundUser.get().getPassword().equals(logInDTO.getPassword())) {
            return true;
        }

        return false;
    }
}
