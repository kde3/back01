package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.PageBlockDTO;
import com.fiveis.leasemates.domain.Pageable;
import com.fiveis.leasemates.domain.dto.community.PostDetailDTO;
import com.fiveis.leasemates.domain.dto.sharehouse.SharehousePostDTO;
import com.fiveis.leasemates.domain.dto.sharehouse.SharehousePostDetailDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SharehouseService {
    List<SharehousePostDTO> postPagination(Pageable pageable);
    PageBlockDTO postPaginationBlock(int blockSize, Pageable pageable);
    SharehousePostDetailDTO getPostDetail(Long postNo, String userNo);
}
