package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.vo.FileVO;
import com.fiveis.leasemates.domain.vo.PostVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface FileService {
    public Long insertFile(FileVO fileVO);
    public void deleteFile(Long fileNo);
//    List<String> uploadFiles(List<MultipartFile> files, Long postNO);
    public void saveFiles(List<FileVO> fileVOs);
    public Long uploadFile(MultipartFile file, FileVO fileVO, PostVO postVO) throws IOException;
}
