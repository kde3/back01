package com.fiveis.leasemates.repository;

import com.fiveis.leasemates.domain.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

@Mapper
public interface UserRepository {
    Optional<UserVO> findByUserId(String id);
    void createUser(UserVO userVO);
}
