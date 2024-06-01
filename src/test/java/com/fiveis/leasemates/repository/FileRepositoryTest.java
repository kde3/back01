package com.fiveis.leasemates.repository;

import com.fiveis.leasemates.domain.vo.FileVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FileRepositoryTest {
    @Autowired
    private FileRepository fileRepository;

    @DisplayName("파일 추가")
    @Test
    void insertFile() {
        Long fileNo = fileRepository.getFileNo();
        FileVO fileVO = FileVO.builder()
                .fileNo(fileNo)
                .postNo(3L)
                .filePath("c:/folder/2.png")
                .build();

        fileRepository.insertFile(fileVO);

        System.out.println("fileVO = " + fileVO);
    }

    @Test
    void deleteFileById() {
        fileRepository.deleteFileById(6L);
    }
}