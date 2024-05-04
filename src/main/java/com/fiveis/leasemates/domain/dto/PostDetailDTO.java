package com.fiveis.leasemates.domain.dto;

import com.fiveis.leasemates.domain.vo.CmtVO;
import com.fiveis.leasemates.domain.vo.PostVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class PostDetailDTO {
    private PostVO postVO;
    private List<CmtVO> cmtVOs;
    private int checkLike;          // 좋아요했는지 확인
}
