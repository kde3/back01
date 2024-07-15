package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.vo.FileVO;
import com.fiveis.leasemates.domain.vo.PostVO;
import com.fiveis.leasemates.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Value("${file.dir}")
    private String fileDir;

    private final FileRepository fileRepository;

    @Override
    @Transactional
    public Long insertFile(FileVO fileVO) {
        Long fileNo = fileRepository.getFileNo();
        FileVO file = FileVO.builder()
                .fileNo(fileNo)
                .filePath(fileDir)
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
    public void saveFiles(List<FileVO> fileVOs) {
        for (FileVO fileVO : fileVOs) {
            fileRepository.insertFile(fileVO);
        }
    }

    @Override
    public Long uploadFile(MultipartFile file, FileVO fileVO, PostVO postVO) throws IOException {
        if(file.isEmpty()){
            return null;
        }

        //원래 이름 추출
        String origName = file.getOriginalFilename();

        //파일 이름으로 쓸 uuid 생성
        String uuid = UUID.randomUUID().toString();

        //확장자 추출(ex : .png)
        String extension = origName.substring(origName.lastIndexOf("."));

        //uuid와 확장자 결합
        String savedName = uuid + extension;

        //파일을 불러올 때 사용할 파일 경로
        String savedPath = fileDir + savedName;


        FileVO files = FileVO.builder()
                .fileNo(fileVO.getFileNo())
                .postNo(postVO.getPostNo())
                .filePath(fileVO.getFilePath())
                .orgName(fileVO.getOrgName())
                .savedName(fileVO.getSavedName())
                .build();

        //실제로 로컬에 uuid를 파일명으로 저장
        file.transferTo(new File(savedPath));

        fileRepository.insertFile(files);
        return fileVO.getFileNo();
    }
}
