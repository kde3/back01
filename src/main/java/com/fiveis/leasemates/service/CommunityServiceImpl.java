package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.dto.PostDetailDTO;
import com.fiveis.leasemates.domain.vo.CmtVO;
import com.fiveis.leasemates.domain.vo.LikeVO;
import com.fiveis.leasemates.domain.vo.PostVO;
import com.fiveis.leasemates.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CommunityRepository communityRepository;

    /**
     * @param postId
     * 커뮤니티 게시물 상세 페이지 보여주기(+ 댓글 목록도)
     */
    @Override
    public PostDetailDTO getPostDetail(Long postId, String id) {
        Optional<PostVO> foundPost = communityRepository.findPostById(postId);

//        커스텀 exception 만들어야 함.
//        게시물을 찾을 수 없습니다.
//        if(foundPost.isEmpty()) throw


        List<CmtVO> foundCmts = null;

        // 댓글이 있다면 가져오기
        if(foundPost.get().getCmtCnt() > 0) {
            foundCmts = communityRepository.findCmtAll(postId);
        }

        // 유저가 게시물에 좋아요했는지 확인하기
        LikeVO likeVO = LikeVO.builder()
                .postNo(postId)
                .userNo(id).build();

        int checkLike = communityRepository.findLikeById(likeVO);

        PostDetailDTO postDetailDTO = new PostDetailDTO();
        postDetailDTO.setPostVO(foundPost.get());
        postDetailDTO.setCmtVOs(foundCmts);
        postDetailDTO.setCheckLike(checkLike);

        return postDetailDTO;
    }

    /**
     * 커뮤니티 댓글 추가
     * @param postId, cmtVO
     * @return cmtNo : 생성된 댓글의 번호
     */
    @Override
    public Long createCmt(Long postId, CmtVO cmtVO) {
        Long cmtNo = communityRepository.getCmtNo();
        CmtVO newCmt = CmtVO.builder()
                .cmtNo(cmtNo)
                .postNo(postId)
                .userNo(cmtVO.getUserNo())
                .updatedAt(LocalDateTime.now())
                .content(cmtVO.getContent())
                .build();

        communityRepository.createCmt(newCmt);

        return cmtNo;
    }

    /**
     * 커뮤니티 댓글 수정
     * @param cmtVO
     */
    @Override
    public void updateCmt(CmtVO cmtVO) {
        CmtVO updatedCmtVO = CmtVO.builder()
                .cmtNo(cmtVO.getCmtNo())
                .content(cmtVO.getContent())
                .updatedAt(LocalDateTime.now())
                .build();

        communityRepository.updateCmt(updatedCmtVO);

        //댓글 개수 업데이트
        communityRepository.updateCmtCnt(cmtVO.getCmtNo());
    }

    /**
     * 커뮤니티 댓글 삭제
     * @param cmtNo
     */
    @Override
    public void deleteCmt(Long cmtNo) {
        communityRepository.deleteCmtById(cmtNo);

        //댓글 개수 업데이트
        communityRepository.updateCmtCnt(cmtNo);
    }

    /**
     * 좋아요 하기
     * @param likeVO
     */
    @Override
    public void createLike(LikeVO likeVO) {
        communityRepository.createLike(likeVO);
        communityRepository.updateLikeCnt(likeVO.getPostNo());
    }

    /**
     * 좋아요 취소
     * @param likeVO
     */
    @Override
    public void deleteLike(LikeVO likeVO) {
        communityRepository.deleteLikeById(likeVO);
        communityRepository.updateLikeCnt(likeVO.getPostNo());
    }
}
