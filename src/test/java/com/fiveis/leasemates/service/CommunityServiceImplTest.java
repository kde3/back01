package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.dto.PostDetailDTO;
import com.fiveis.leasemates.domain.vo.CmtVO;
import com.fiveis.leasemates.domain.vo.LikeVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommunityServiceImplTest {
    @Autowired
    CommunityService communityService;

    @Test
    @DisplayName("게시물 상세 보기")
    void getPostDetail() {
        PostDetailDTO postDetail = communityService.getPostDetail(4L, "uuidtest");
        System.out.println(postDetail.getPostVO());
        System.out.println(postDetail.getCmtVOs());
        System.out.println(postDetail.getCheckLike());
    }

    @Test
    @DisplayName("댓글 생성")
    void createCmt() {
        CmtVO cmtVO = CmtVO.builder()
                .userNo("uuidtest")
                .content("댓글이당")
                .build();

        Long cmtNo = communityService.createCmt(4L, cmtVO);

        System.out.println("cmtNo = " + cmtNo);
    }

    @Test
    @DisplayName("댓글 수정")
    void updateCmt() {
        CmtVO cmtVO = CmtVO.builder()
                .postNo(4L)
                .cmtNo(2L)
                .content("댓글수정이당")
                .build();
        communityService.updateCmt(cmtVO);
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteCmt() {
        communityService.deleteCmt(2L);
    }

    @Test
    @DisplayName("좋아요 하기")
    void createLike() {
        LikeVO likeVO = LikeVO.builder()
                .postNo(4L)
                .userNo("uuidtest").build();

        communityService.createLike(likeVO);
    }

    @Test
    @DisplayName("좋아요 취소")
    void deleteLike() {
        LikeVO likeVO = LikeVO.builder()
                .postNo(4L)
                .userNo("uuidtest").build();

        communityService.deleteLike(likeVO);
    }
}