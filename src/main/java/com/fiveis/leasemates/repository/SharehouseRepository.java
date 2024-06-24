package com.fiveis.leasemates.repository;

import com.fiveis.leasemates.domain.Pageable;
import com.fiveis.leasemates.domain.dto.sharehouse.SharehousePostDTO;
import com.fiveis.leasemates.domain.vo.sharehouse.SharehousePostVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SharehouseRepository {
    void createDummy(SharehousePostVO sharehousePostVO);
    List<SharehousePostDTO> postPagination(Pageable pageable);
    int findPostAllCount();
}
