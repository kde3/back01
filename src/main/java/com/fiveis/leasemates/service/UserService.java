package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.dto.LogInDTO;
import com.fiveis.leasemates.domain.vo.UserVO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    //회원가입
    Boolean join(UserVO userVO);
}
