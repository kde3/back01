package com.fiveis.leasemates.domain.dto.community;

import com.fiveis.leasemates.domain.vo.CmtVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CmtDTO {
    private Long cmtNo;
    private String userNo;
    private String userName;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String isUpdated;

    public static CmtDTO from(CmtVO cmtVO) {
        CmtDTO dto = new CmtDTO();
        dto.setCmtNo(cmtVO.getCmtNo());
        dto.setUserNo(cmtVO.getUserNo());
        dto.setContent(cmtVO.getContent());
        dto.setCreatedAt(cmtVO.getCreatedAt());
        dto.setUpdatedAt(cmtVO.getUpdatedAt());
        dto.setIsUpdated(cmtVO.getIsUpdated());

        return dto;
    }
}
