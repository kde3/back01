package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.vo.FileVO;
import com.fiveis.leasemates.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    @Transactional
    public Long insertFile(FileVO fileVO) {
        Long fileNo = fileRepository.getFileNo();
        FileVO file = FileVO.builder()
                .fileNo(fileNo)
                .filePath("C:/upload/commu/1.png")
                .postNo(fileVO.getPostNo())
                .build();

        System.out.println(file);
        return fileNo;
    }

    @Override
    public void deleteFile(Long fileNo) {
        fileRepository.deleteFileById(fileNo);
    }

    @Override
    @Transactional
    public List<String> uploadFiles(List<MultipartFile> files, Long postNO) {
        List <String> fileUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;
            try {
                // 파일 업로드 및 URL 생성 로직
                String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "-" + file.getOriginalFilename();
                String filePath = "c:/upload/community/" + fileName;

                // 파일 저장 경로 설정
                Path path = Paths.get(filePath);
                Files.copy(file.getInputStream(), path);

                fileUrls.add(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileUrls;
    }

    @Override
    public void saveFiles(List<FileVO> fileVOs) {
        for (FileVO fileVO : fileVOs) {
            fileRepository.insertFile(fileVO);
        }
    }
}
