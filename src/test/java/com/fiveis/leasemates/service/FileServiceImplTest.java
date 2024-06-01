package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.vo.FileVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileServiceTest {

    @Autowired
    FileService fileService;

    @Test
    void insertFile() {
        FileVO fileVO = FileVO.builder()
                .fileNo(1L)
                .filePath("C:/upload/commu/1.png")
                .postNo(10L)
                .build();

        fileService.insertFile(fileVO);
    }

    @Test
    void deleteFile() {
        long fileNo = 10L;
        fileService.deleteFile(fileNo);
    }
}