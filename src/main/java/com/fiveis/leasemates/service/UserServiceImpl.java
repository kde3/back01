package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.vo.UserVO;
import com.fiveis.leasemates.repository.UserRepository;
import com.fiveis.leasemates.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 회원가입
     */
    public Boolean join(UserVO userVO) {
        // 중복불가 필드 검사
        //아이디 조회해서 검색되면 탈락
        Optional<UserVO> foundUser = userRepository.findByUserId(userVO.getId());

        if(foundUser.isPresent()) {
            System.out.println("아이디 중복");
            return false;
        }

        //통과되었다면 객체에 담아 저장
        UUID uuid = UUID.randomUUID();
        String encodePassword = bCryptPasswordEncoder.encode(userVO.getPassword());

        UserVO newUser = UserVO.builder()
                .userNo(uuid.toString())
                .id(userVO.getId())
                .role(Role.USER.getKey())
                .name(userVO.getName())
                .password(encodePassword)
                .email(userVO.getEmail())
                .phoneNumber(userVO.getPhoneNumber())
                .createdAt(LocalDate.now()).build();

        userRepository.createUser(newUser);

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        UserVO userVO = userRepository.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException("없는 유저입니다."));

        UserDetails userDetails = User.builder()
                .username(userVO.getId())
                .password(userVO.getPassword())
                .roles(userVO.getRole())
                .build();

        return userDetails;
    }
}
