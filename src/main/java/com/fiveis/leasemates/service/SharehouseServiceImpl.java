package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.PageBlockDTO;
import com.fiveis.leasemates.domain.Pageable;
import com.fiveis.leasemates.domain.dto.community.CmtDTO;
import com.fiveis.leasemates.domain.dto.community.PostDetailDTO;
import com.fiveis.leasemates.domain.dto.sharehouse.SharehousePostDTO;
import com.fiveis.leasemates.domain.dto.sharehouse.SharehousePostDetailDTO;
import com.fiveis.leasemates.domain.vo.CmtVO;
import com.fiveis.leasemates.domain.vo.LikeVO;
import com.fiveis.leasemates.domain.vo.PostVO;
import com.fiveis.leasemates.domain.vo.sharehouse.SharehousePostVO;
import com.fiveis.leasemates.repository.SharehouseRepository;
import com.fiveis.leasemates.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SharehouseServiceImpl implements SharehouseService {
    private final SharehouseRepository sharehouseRepository;
    private final UserRepository userRepository;

    @Override
    public List<SharehousePostDTO> postPagination(Pageable pageable) {
        List<SharehousePostDTO> sharehousePostDTOList = sharehouseRepository.postPagination(pageable);

        // 닉네임 담기
        for(SharehousePostDTO sharehousePostDTO : sharehousePostDTOList) {
            sharehousePostDTO.setUserName(userRepository.findNameByUserNo(sharehousePostDTO.getUserNo()));
        }

        return sharehousePostDTOList;
    }

    @Override
    public PageBlockDTO postPaginationBlock(int blockSize, Pageable pageable) {
        int postTotal = sharehouseRepository.findPostAllCount();
        int totalPages = (int) Math.ceil(postTotal / (double) pageable.getPageSize());  // 전체 버튼 개수

        return new PageBlockDTO(blockSize, totalPages, pageable);
    }

    @Override
    public SharehousePostDetailDTO getPostDetail(Long postNo, String userNo) {
        SharehousePostVO foundPost = sharehouseRepository.findPostById(postNo)
                .orElseThrow(() -> new IllegalStateException("게시물이 존재하지 않습니다."));

        String username = userRepository.findNameByUserNo(foundPost.getUserNo());

        // 유저가 게시물에 좋아요했는지 확인하기
        LikeVO likeVO = LikeVO.builder()
                .postNo(postNo)
                .userNo(userNo).build();
        int checkLike = sharehouseRepository.findLikeById(likeVO);

        SharehousePostDetailDTO sharehousePostDetailDTO = SharehousePostDetailDTO.from(foundPost);
        sharehousePostDetailDTO.setUserName(username);
        sharehousePostDetailDTO.setCheckLike(checkLike);

        return sharehousePostDetailDTO;
    }
}
