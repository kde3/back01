package com.fiveis.leasemates.controller;

import com.fiveis.leasemates.domain.dto.PostDetailDTO;
import com.fiveis.leasemates.domain.vo.CmtVO;
import com.fiveis.leasemates.domain.vo.LikeVO;
import com.fiveis.leasemates.service.CommunityService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService communityService;

    /**
     * 메인화면(로그인 전)
     * http://localhost:10000/community/
     * @return
     */
//    @GetMapping("/")
//    public String mainBeforeLoginView() {
//        return "commu/index/before/before";
//    }

    /**
     * 메인화면(로그인 후)
     * http://localhost:10000/community/
     * @return
     */
    @GetMapping("/")
    public String mainAfterLoginView() {
        return "commu/index/after/after";
    }

    /**
     * 게시글 상세페이지
     * http://localhost:10000/community/1
     * @return
     */
    @GetMapping("/{postNo}")
    @ResponseBody
    public ModelAndView postDetailView(@PathVariable("postNo") Long postNo) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("commu/post/detail/detail");

        String userNo = "uuidtest1";     //유저 고유 넘버(임시)

        PostDetailDTO postDetail = communityService.getPostDetail(postNo, userNo);
//        board.setContent(content.replaceAll("\n", "<br>"));
        mv.addObject(postDetail);

        return mv;
//        return ResponseEntity.status(HttpStatus.OK).body(postDetail);
    }

    /**
     *  좋아요 하기
     */
    @PostMapping("/{postNo}/like")
    public ResponseEntity<String> createLike(@PathVariable("postNo") Long postNo) {
        String userNo = "uuidtest1";

        LikeVO likeVO = LikeVO.builder()
                .postNo(postNo)
                .userNo(userNo)
                .build();

        communityService.createLike(likeVO);

        return ResponseEntity.status(HttpStatus.OK).body("좋아요");
    }

    /**
     *  좋아요 취소
     */
    @DeleteMapping("/{postNo}/like")
    public ResponseEntity<String> deleteLike(@PathVariable("postNo") Long postNo) {
        String userNo = "uuidtest1";

        LikeVO likeVO = LikeVO.builder()
                .postNo(postNo)
                .userNo(userNo)
                .build();

        communityService.deleteLike(likeVO);

        return ResponseEntity.status(HttpStatus.OK).body("좋아요 취소");
    }


    /**
     * 댓글 보기
     * http://localhost:10000/community/4/cmt
     *
     */
    @GetMapping("/{postNo}/cmt")
    @ResponseBody
    public ResponseEntity<List<CmtVO>> cmtView(@PathVariable("postNo") Long postNo) {
        List<CmtVO> cmtVOs = communityService.findCmtAll(postNo);

        return ResponseEntity.status(HttpStatus.CREATED).body(cmtVOs);
    }

    /**
     * 댓글 추가
     * /community/{postId}/cmt
     */
    @PostMapping("/{postNo}/cmt")
    public ResponseEntity<List<CmtVO>> createCmt(@PathVariable("postNo") Long postNo, @RequestBody CmtVO cmtVO) {
        CmtVO newCmt = CmtVO.builder()
                .postNo(postNo)
                .userNo(cmtVO.getUserNo())
                .content(cmtVO.getContent())
                .build();

        List<CmtVO> cmtVOs = communityService.createCmt(newCmt);

        return ResponseEntity.status(HttpStatus.CREATED).body(cmtVOs);
    }
}
