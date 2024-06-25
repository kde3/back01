package com.fiveis.leasemates.repository;

import com.fiveis.leasemates.domain.Pageable;
import com.fiveis.leasemates.domain.dto.community.CmtDTO;
import com.fiveis.leasemates.domain.dto.community.PostDTO;
import com.fiveis.leasemates.domain.vo.CmtVO;
import com.fiveis.leasemates.domain.vo.LikeVO;
import com.fiveis.leasemates.domain.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommunityRepository {
    void createDummyPost(PostVO postVO);
    void createDummyCmt(CmtVO cmtVO);

    Long getPostNo();

    void createPost(PostVO postVO);

    List<PostVO> findPostAll();

    int findPostAllCount();

    List<PostDTO> postPagination(Pageable pageable);

    Optional<PostVO> findPostById(Long postNo);

    void updatePost(PostVO postVO);

    void deletePostById(Long postNo);

//    PostVO savePost(Optional<PostVO> postVO);

    /**
     * 댓글 관련 기능
     */
    Long getCmtNo();

    void createCmt(CmtVO cmtVO);

    List<CmtVO> findCmtAll(Long postNo);

    List<CmtVO> cmtPagination(@Param("postNo") Long postNo,
                               @Param("pageable") Pageable pageable);

    int findCmtAllCount(Long postNo);

    Optional<CmtVO> findCmtById(Long cmtNo);

    void updateCmt(CmtVO cmtVO);

    void deleteCmtById(Long cmtNo);

    void updateCmtCnt(Long postNo);

    /**
     * 좋아요 관련 기능
     */
    int findLikeById(LikeVO likeVO);        //좋아요 했는지 확인 위함

    void updateLikeCnt(Long postNo);

    void createLike(LikeVO likeVO);

    void deleteLikeById(LikeVO likeVO);
}
