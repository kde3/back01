package com.fiveis.leasemates.repository;

import com.fiveis.leasemates.domain.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserRepository {
    Optional<UserVO> findById(String id);
    void createUser(UserVO userVO);
}
