package com.fiveis.leasemates.domain.dto.sharehouse;

import com.fiveis.leasemates.domain.dto.community.CmtDTO;
import com.fiveis.leasemates.domain.dto.community.PostDetailDTO;
import com.fiveis.leasemates.domain.vo.PostVO;
import com.fiveis.leasemates.domain.vo.sharehouse.SharehousePostVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SharehousePostDetailDTO {
    private Long postNo;
    private String userNo;
    private String userName;            //작성자 닉네임
    private String title;
    private String content;
    private String address;
    private String createdAt;
    private String updatedAt;
    private String isUpdated;
    private Integer likeCnt = 0;
    private int checkLike;              // 본인이 좋아요했는지 확인

    //    Entity -> DTO
    public static SharehousePostDetailDTO from(SharehousePostVO sharehousePostVO) {
        SharehousePostDetailDTO dto = new SharehousePostDetailDTO();

        dto.setPostNo(sharehousePostVO.getPostNo());
        dto.setUserNo(sharehousePostVO.getUserNo());
        dto.setTitle(sharehousePostVO.getTitle());
        dto.setCreatedAt(sharehousePostVO.getCreatedAt());
        dto.setUpdatedAt(sharehousePostVO.getUpdatedAt());
        dto.setIsUpdated(sharehousePostVO.getIsUpdated());
        dto.setContent(sharehousePostVO.getContent());
        dto.setAddress(sharehousePostVO.getAddress());
        dto.setLikeCnt(sharehousePostVO.getLikeCnt());

        return dto;
    }
}
