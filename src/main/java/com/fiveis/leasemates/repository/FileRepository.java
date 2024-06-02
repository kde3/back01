package com.fiveis.leasemates.repository;

import com.fiveis.leasemates.domain.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileRepository {

    Long getFileNo();
    void insertFile(FileVO fileVO);
    void deleteFileById(Long fileNo);

}
