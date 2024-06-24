package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.PageBlockDTO;
import com.fiveis.leasemates.domain.Pageable;
import com.fiveis.leasemates.domain.dto.sharehouse.SharehousePostDTO;
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
}
