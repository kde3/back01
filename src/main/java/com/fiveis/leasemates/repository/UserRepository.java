package com.fiveis.leasemates.repository;

import com.fiveis.leasemates.domain.Pageable;
import com.fiveis.leasemates.domain.dto.community.PostDTO;
import com.fiveis.leasemates.domain.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserRepository {
    String findNameByUserNo(String userNo);
    Optional<UserVO> findByUserId(String id);
    void createUser(UserVO userVO);

    List<PostDTO> userPostPagination(@Param("userNo") String userNo, @Param("pageable") Pageable pageable);

    int findMyPostAllCount(String userNo);
}

