package com.fiveis.leasemates.repository;

import com.fiveis.leasemates.domain.Pageable;
import com.fiveis.leasemates.domain.dto.sharehouse.SharehousePostDTO;
import com.fiveis.leasemates.domain.vo.LikeVO;
import com.fiveis.leasemates.domain.vo.sharehouse.SharehousePostVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SharehouseRepository {
    void createDummy(SharehousePostVO sharehousePostVO);

    /**
     * 게시물 목록 보기 관련 기능
     */
    List<SharehousePostDTO> postPagination(Pageable pageable);
    int findPostAllCount();

    /**
     * 게시물 상세 보기 관련 기능
     */
    Optional<SharehousePostVO> findPostById(Long postNo);

    /**
     * 좋아요 관련 기능
     */
    int findLikeById(LikeVO likeVO);        //좋아요 했는지 확인 위함

    void updateLikeCnt(Long postNo);

    void createLike(LikeVO likeVO);

    void deleteLikeById(LikeVO likeVO);
}
