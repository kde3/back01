package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.dto.user.JoinDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    //회원가입
    Boolean join(JoinDTO joinDTO);
}
