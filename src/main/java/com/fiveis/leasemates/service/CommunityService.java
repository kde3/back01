package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.dto.PostDetailDTO;
import com.fiveis.leasemates.domain.vo.CmtVO;
import com.fiveis.leasemates.domain.vo.LikeVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommunityService {

    //커뮤니티 게시물 상세 페이지 보여주기(+ 댓글 목록도)
    PostDetailDTO getPostDetail(Long postId, String id);

    //커뮤니티 댓글 목록 보기
    List<CmtVO> findCmtAll(Long postNo);

    //커뮤니티 댓글 추가
    List<CmtVO> createCmt(CmtVO cmtVO);

    //커뮤니티 댓글 수정
    void updateCmt(CmtVO cmtVO);

    //커뮤니티 댓글 삭제
    void deleteCmt(Long cmtNo);

    //좋아요 하기
    void createLike(LikeVO likeVO);

    //좋아요 취소
    void deleteLike(LikeVO likeVO);
}
