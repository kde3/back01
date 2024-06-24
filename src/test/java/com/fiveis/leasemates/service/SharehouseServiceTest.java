package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.Pageable;
import com.fiveis.leasemates.domain.dto.sharehouse.SharehousePostDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SharehouseServiceTest {
    @Autowired
    private SharehouseService sharehouseService;

    @Test
    @DisplayName("sharehouse 게시물 페이지네이션")
    void postPagination() {
        Pageable pageable = new Pageable();
        pageable.setPage(1);
        pageable.setPageSize(5);

        List<SharehousePostDTO> sharehousePostDTOList = sharehouseService.postPagination(pageable);
        for (SharehousePostDTO sharehousePostDTO : sharehousePostDTOList)
            System.out.println("sharehousePostDTO = " + sharehousePostDTO);
    }
}