package com.fiveis.leasemates.repository;

import com.fiveis.leasemates.domain.vo.CmtVO;
import com.fiveis.leasemates.domain.vo.LikeVO;
import com.fiveis.leasemates.domain.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommunityRepository {
    /**
     * 게시물 관련 기능
     */
    Long getPostNo();

    void createPost(PostVO postVO);

    List<PostVO> findPostAll();

    Optional<PostVO> findPostById(Long postNo);

    void updatePost(PostVO postVO);

    void deletePostById(Long postNo);

    /**
     * 댓글 관련 기능
     */
    Long getCmtNo();

    void createCmt(CmtVO cmtVO);

    List<CmtVO> findCmtAll();

    Optional<CmtVO> findCmtById(Long cmtNo);

    void updateCmt(CmtVO cmtVO);

    void deleteCmtById(Long cmtNo);

    /**
     * 좋아요 관련 기능
     */
    int findLikeById(LikeVO likeVO);        //좋아요 했는지 확인 위함

    void createLike(LikeVO likeVO);

    void deleteLikeById(LikeVO likeVO);
}
