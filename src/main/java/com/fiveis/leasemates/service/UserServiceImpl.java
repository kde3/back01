package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.dto.user.JoinDTO;
import com.fiveis.leasemates.domain.vo.UserVO;
import com.fiveis.leasemates.repository.UserRepository;
import com.fiveis.leasemates.security.CustomUserDetails;
import com.fiveis.leasemates.security.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 회원가입
     */
    public Boolean join(JoinDTO joinDTO) {
        // 중복불가 필드 검사
        // 아이디 조회해서 검색되면 탈락
        Optional<UserVO> foundUser = userRepository.findByUserId(joinDTO.getId());

        if(foundUser.isPresent()) {
            log.info("아이디 중복");
            return false;
        }

        String uuid = UUID.randomUUID().toString();
        String encodePassword = bCryptPasswordEncoder.encode(joinDTO.getPassword());

        UserVO newUser = UserVO.builder()
                .userNo(uuid)
                .id(joinDTO.getId())
                .role(Role.USER.getKey())
                .name(joinDTO.getName())
                .password(encodePassword)
                .email(joinDTO.getEmail())
                .phoneNumber(joinDTO.getPhoneNumber())
                .build();

        userRepository.createUser(newUser);

        return true;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserVO userVO = userRepository.findByUserId(id)
                .orElseThrow(() -> new UsernameNotFoundException("없는 유저입니다."));
        return new CustomUserDetails(userVO);

//        UserVO userVO = userRepository.findByUserId(id).orElseThrow(() -> new UsernameNotFoundException("없는 유저입니다."));
//
//        UserDetails userDetails = User.builder()
//                .username(userVO.getId())
//                .password(userVO.getPassword())
//                .roles(userVO.getRole())
//                .build();
//
//        return userDetails;
    }
}
