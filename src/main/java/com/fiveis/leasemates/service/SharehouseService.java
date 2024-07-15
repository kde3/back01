package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.PageBlockDTO;
import com.fiveis.leasemates.domain.Pageable;
import com.fiveis.leasemates.domain.dto.sharehouse.SharehousePostDTO;
import com.fiveis.leasemates.domain.dto.sharehouse.SharehousePostDetailDTO;
import com.fiveis.leasemates.domain.vo.FileVO;
import com.fiveis.leasemates.domain.vo.LikeVO;
import com.fiveis.leasemates.domain.vo.sharehouse.SharehousePostVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public interface SharehouseService {
    List<SharehousePostDTO> postPagination(Pageable pageable);
    PageBlockDTO postPaginationBlock(int blockSize, Pageable pageable);
    SharehousePostDetailDTO getPostDetail(Long postNo, String userNo);
    void createLike(LikeVO likeVO);
    void deleteLike(LikeVO likeVO);
    Long createPost(SharehousePostVO sharehousePostVO, List<MultipartFile> files);
    public void updatePost(SharehousePostVO sharehousePostVO, List<MultipartFile> files, FileVO fileVO);
    public void deletePost(long postNo);
    Optional<SharehousePostVO> findById(Long postNO);
}
