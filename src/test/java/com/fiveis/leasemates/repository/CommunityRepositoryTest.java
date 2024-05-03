package com.fiveis.leasemates.repository;

import com.fiveis.leasemates.domain.vo.CmtVO;
import com.fiveis.leasemates.domain.vo.LikeVO;
import com.fiveis.leasemates.domain.vo.PostVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommunityRepositoryTest {
    @Autowired
    private CommunityRepository communityRepository;

    /**
     * 게시물 관련 기능
     */
    @Test
    @DisplayName("게시물 생성")
    void createPost() {
        Long postNo = communityRepository.getPostNo();
        PostVO postVO = PostVO.builder()
                .postNo(postNo)
                .userNo("uuidtest")
                .title("게시물 제목")
                .updatedAt(LocalDateTime.now())
                .content("게시물 내용")
                .build();

        communityRepository.createPost(postVO);

        System.out.println("postVO = " + postVO);
    }

    @Test
    void findAll() {
        List<PostVO> posts = communityRepository.findPostAll();

        for(PostVO post : posts) {
            System.out.println("post = " + post);
        }
    }

    @Test
    void findById() {
        Optional<PostVO> post = communityRepository.findPostById(2L);
        System.out.println("post = " + post.get());
    }

    @Test
    void updatePost() {
        PostVO postVO = PostVO.builder()
                .postNo(2L)
                .title("게시물 제목 수정")
                .updatedAt(LocalDateTime.now())
                .content("게시물 내용 수정")
                .build();

        communityRepository.updatePost(postVO);

        System.out.println("postVO = " + postVO);
    }

    @Test
    void deletePostById() {
        communityRepository.deletePostById(2L);
    }


    /**
     * 댓글관련 기능
     */

    @Test
    @DisplayName("댓글 생성")
    void createCmt() {
        Long cmtNo = communityRepository.getCmtNo();

        CmtVO cmtVO = CmtVO.builder()
                .cmtNo(cmtNo)
                .postNo(3L)
                .userNo("uuidtest")
                .updatedAt(LocalDateTime.now())
                .content("댓글 내용")
                .build();

        communityRepository.createCmt(cmtVO);

        System.out.println("cmtVO = " + cmtVO);
    }

    @Test
    @DisplayName("한 게시물의 댓글들 조회")
    void findCmtAll() {
        List<CmtVO> cmts = communityRepository.findCmtAll();

        for(CmtVO cmt : cmts) {
            System.out.println("cmt = " + cmt);
        }
    }

    @Test
    @DisplayName("댓글 1개 조회")
    void findCmtById() {
        Optional<CmtVO> cmtVO = communityRepository.findCmtById(1L);
        System.out.println("cmtVO = " + cmtVO.get());
    }

    @Test
    @DisplayName("댓글 수정")
    void updateCmt() {
        CmtVO cmtVO = CmtVO.builder()
                .cmtNo(1L)
                .updatedAt(LocalDateTime.now())
                .content("댓글 내용 수정")
                .build();

        communityRepository.updateCmt(cmtVO);

        System.out.println("cmtVO = " + cmtVO);
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteCmtById() {
        communityRepository.deleteCmtById(1L);
    }

    /**
     * 좋아요 관련 테스트
     */
    @Test
    @DisplayName("게시물 좋아요 했는지 여부")
    void findLikeById() {
        LikeVO likeVO = LikeVO.builder()
                .postNo(3L)
                .userNo("uuidtest")
                .build();

        int checkLike = communityRepository.findLikeById(likeVO);
        System.out.println("checkLike = " + checkLike);
    }

    @Test
    @DisplayName("좋아요 하기")
    void createLike() {
        LikeVO likeVO = LikeVO.builder()
                .postNo(3L)
                .userNo("uuidtest")
                .build();

        communityRepository.createLike(likeVO);
    }

    @Test
    @DisplayName("좋아요 취소")
    void deleteLikeById() {
        LikeVO likeVO = LikeVO.builder()
                .postNo(3L)
                .userNo("uuidtest")
                .build();

        communityRepository.deleteLikeById(likeVO);
    }

}