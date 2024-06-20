package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.dto.community.CmtDTO;
import com.fiveis.leasemates.domain.dto.community.PostDetailDTO;
import com.fiveis.leasemates.domain.vo.CmtVO;
import com.fiveis.leasemates.domain.vo.LikeVO;
import com.fiveis.leasemates.domain.vo.PostVO;
import com.fiveis.leasemates.repository.CommunityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CommunityServiceImplTest {
    @Autowired
    CommunityService communityService;
    @Autowired
    CommunityRepository communityRepository;

//    @Test
//    @DisplayName("게시물 상세 보기")
//    void getPostDetail() {
//        PostDetailDTO postDetail = communityService.getPostDetail(4L, "uuidtest");
//        System.out.println(postDetail);
//    }

    @Test
    @DisplayName("댓글 목록 보기")
    void 댓글목록보기() {
        List<CmtDTO> cmtAll = communityService.findCmtAll(4L);
        for(CmtDTO cmtDTO : cmtAll) {
            System.out.println("cmtDTO = " + cmtDTO);
        }
    }

//    @Test
//    @DisplayName("댓글 생성")
//    void createCmt() {
//        CmtVO cmtVO = CmtVO.builder()
//                .userNo("uuidtest")
//                .content("댓글이당")
//                .build();
//
//        Long cmtNo = communityService.createCmt(4L, cmtVO);
//
//        System.out.println("cmtNo = " + cmtNo);
//    }

//    @Test
//    @DisplayName("댓글 수정")
//    void updateCmt() {
//        CmtVO cmtVO = CmtVO.builder()
//                .postNo(4L)
//                .cmtNo(2L)
//                .content("댓글수정이당")
//                .build();
//        communityService.updateCmt(cmtVO);
//    }
//
//    @Test
//    @DisplayName("댓글 삭제")
//    void deleteCmt() {
//        communityService.deleteCmt(2L);
//    }
//
//    @Test
//    @DisplayName("좋아요 하기")
//    void createLike() {
//        LikeVO likeVO = LikeVO.builder()
//                .postNo(4L)
//                .userNo("uuidtest").build();
//
//        communityService.createLike(likeVO);
//    }
//
//    @Test
//    @DisplayName("좋아요 취소")
//    void deleteLike() {
//        LikeVO likeVO = LikeVO.builder()
//                .postNo(4L)
//                .userNo("uuidtest").build();
//
//        communityService.deleteLike(likeVO);
//    }
//
//    @Test
//    @DisplayName("게시글 작성")
//    void createPost() {
//        List<MultipartFile> files = new ArrayList<>();
////        System.out.println(files);
//        PostVO postVO = PostVO.builder()
//                .postNo(communityRepository.getPostNo())
//                .userNo("uuidtest1")
//                .title("12번째 제목")
//                .content("안녕하세요 내용입니다")
//                .updatedAt(LocalDateTime.now())
//                .build();
//
//        communityService.createPost(postVO, files);
//    }
//
//    @Test
//    @DisplayName("게시글 목록 보기")
//    void findPostAll() {
//        List<PostVO> postList = communityService.findPostAll();
//        System.out.println("전체 게시물 : " + postList);
//        System.out.println("전체 게시물 개수 : " + postList.toArray().length);
//    }
//
//    @Test
//    @DisplayName("게시글 수정")
//    void updatePost() {
//        List<MultipartFile> files = new ArrayList<>();
//        PostVO postVO = PostVO.builder()
//                .postNo(114L) // 수정할 게시물 번호
//                .userNo("uuidtest1")
//                .title("1시 53분 수정제목")
//                .content("수정된 내용입니다")
//                .updatedAt(LocalDateTime.now())
//                .build();
//
//        communityService.updatePost(postVO, files);
//    }
//
//    @Test
//    @DisplayName("게시글 삭제")
//    void deletePost() {
//        long postNo = 49L; // 삭제할 게시물 번호
//        communityService.deletePost(postNo);
//    }
}