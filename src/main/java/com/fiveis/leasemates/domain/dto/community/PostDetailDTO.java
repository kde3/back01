package com.fiveis.leasemates.domain.dto.community;

import com.fiveis.leasemates.domain.vo.PostVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class PostDetailDTO {
    private Long postNo;
    private String userNo;
    private String userName;            //작성자 닉네임
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String isUpdated;
    private Integer likeCnt = 0;
    private Integer CmtCnt = 0;
    private int checkLike;              // 본인이 좋아요했는지 확인
    private List<CmtDTO> cmtDTOList = null;

//    Entity -> DTO
    public static PostDetailDTO from(PostVO postVO) {
        PostDetailDTO dto = new PostDetailDTO();

        dto.setPostNo(postVO.getPostNo());
        dto.setUserNo(postVO.getUserNo());
        dto.setTitle(postVO.getTitle());
        dto.setCreatedAt(postVO.getCreatedAt());
        dto.setUpdatedAt(postVO.getUpdatedAt());
        dto.setIsUpdated(postVO.getIsUpdated());
        dto.setContent(postVO.getContent());
        dto.setLikeCnt(postVO.getLikeCnt());
        dto.setCmtCnt(postVO.getCmtCnt());

        return dto;
    }
}
