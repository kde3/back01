package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.PageBlockDTO;
import com.fiveis.leasemates.domain.Pageable;
import com.fiveis.leasemates.domain.dto.community.PostDTO;
import com.fiveis.leasemates.domain.dto.user.JoinDTO;
import com.fiveis.leasemates.domain.vo.UserVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    @DisplayName("회원가입")
    void join() {
        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setId("test");
        joinDTO.setName("test");
        joinDTO.setPassword("1234");
        joinDTO.setEmail("test@naver.com");
        joinDTO.setPhoneNumber("010-1111-1111");

        Boolean join = userService.join(joinDTO);
    }



    @Test
    @DisplayName("내 게시글 페이지네이션")
    void userPostPagination(){
        Pageable pageable = new Pageable();
        pageable.setPage(1);
        pageable.setPageSize(3);

        List<PostDTO> postDTOList = userService.userPostPagination("f00586b2-e0d0-4a44-a05b-95177a752350", pageable);
    }


    @Test
    @DisplayName("나의 게시글 페이지네이션 버튼 뿌리기")
    void userPostPaginationBlock(){
        Pageable pageable = new Pageable();
        pageable.setPage(1);
        pageable.setPageSize(3);

        PageBlockDTO pageBlockDTO = userService.userPostPaginationBlock(5, pageable, "f00586b2-e0d0-4a44-a05b-95177a752350");
        System.out.println("pageBlockDTO = " + pageBlockDTO);
        
    }








}