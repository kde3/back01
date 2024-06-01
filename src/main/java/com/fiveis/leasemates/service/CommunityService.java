package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.PageBlockDTO;
import com.fiveis.leasemates.domain.Pageable;
import com.fiveis.leasemates.domain.dto.community.CmtDTO;
import com.fiveis.leasemates.domain.dto.community.PostDTO;
import com.fiveis.leasemates.domain.dto.community.PostDetailDTO;
import com.fiveis.leasemates.domain.vo.CmtVO;
import com.fiveis.leasemates.domain.vo.LikeVO;
import com.fiveis.leasemates.domain.vo.PostVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public interface CommunityService {

    //커뮤니티 게시물 상세 페이지 보여주기
    PostDetailDTO getPostDetail(Long postId, String id, Pageable pageable);

    //커뮤니티 댓글 목록 보기
    List<CmtDTO> findCmtAll(Long postNo);

    //커뮤니티 댓글 추가
    List<CmtVO> createCmt(CmtVO cmtVO);

    //커뮤니티 댓글 수정
    void updateCmt(Long postNo, CmtVO cmtVO);

    //커뮤니티 댓글 삭제
    void deleteCmt(Long postNo, Long cmtNo);

    //좋아요 하기
    void createLike(LikeVO likeVO);

    //좋아요 취소
    void deleteLike(LikeVO likeVO);

    //게시글 목록 페이지네이션 적용
    List<PostDTO> postPagination(Pageable pageable);

    // 게시글 페이지네이션 블록 반환
    PageBlockDTO postPaginationBlock(int blockSize, Pageable pageable);

    // 게시글 상세페이지 댓글 페이지네이션 블록 반환
    PageBlockDTO cmtPaginationBlock(Long postNo, int blockSize, Pageable pageable);

    Long createPost(PostVO postVO, List<MultipartFile> files);
    List<PostVO> findPostAll();

    public void updatePost(PostVO postVO, List<MultipartFile> files);
    public void deletePost(long postNo);
    Optional<PostVO> findById(Long postNO);
    //수정한 내용 저장
//    PostVO savePost(Long PostNo, PostVO savePost);
}
