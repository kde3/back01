package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.Pageable;
import com.fiveis.leasemates.domain.dto.sharehouse.SharehousePostDTO;
import com.fiveis.leasemates.domain.dto.sharehouse.SharehousePostDetailDTO;
import com.fiveis.leasemates.domain.vo.FileVO;
import com.fiveis.leasemates.domain.vo.sharehouse.SharehousePostVO;
import com.fiveis.leasemates.repository.SharehouseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SharehouseServiceTest {
    @Autowired
    private SharehouseService sharehouseService;
    @Autowired
    private SharehouseRepository sharehouseRepository;

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

    @Test
    @DisplayName("sharehouse 게시물 상세 보기 페이지")
    void getPostDetail() {
        SharehousePostDetailDTO sharehousePostDetailDTO = sharehouseService.getPostDetail(1L, "f00586b2-e0d0-4a44-a05b-95177a752350");
        System.out.println("sharehousePostDetailDTO = " + sharehousePostDetailDTO);
    }

    @Test
    @DisplayName("sharehouse 게시물 작성")
    void createPost(){
        List<MultipartFile> files = new ArrayList<>();
        SharehousePostVO sharehousePostVO = SharehousePostVO.builder()
                .postNo(sharehouseRepository.getPostNo())
                .userNo("aaa")
                .title("제목입니다")
                .content("내용입니다")
                .updatedAt("N")
                .build();

        sharehouseService.createPost(sharehousePostVO, files);
    }

    @Test
    @DisplayName("sharehouse 게시물 수정")
    void updatePost(FileVO fileVO){
        List<MultipartFile> files = new ArrayList<>();
        SharehousePostVO sharehousePostVO = SharehousePostVO.builder()
                .postNo(1L) //수정할 게시물 번호
                .userNo("aaa")
                .title("제목입니다")
                .content("내용입니다")
                .updatedAt("Y")
                .build();

        sharehouseService.updatePost(sharehousePostVO, files, fileVO);
    }

    @Test
    @DisplayName("게시글 삭제")
    void deletePost() {
        long postNo = 1L; // 삭제할 게시물 번호
        sharehouseService.deletePost(postNo);
    }
}