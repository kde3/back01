package com.fiveis.leasemates.domain.dto.sharehouse;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SharehousePostDTO {
    private Long postRownum;            //rownum으로 post 번호 매긴 것
    private Long postNo;
    private String userNo;
    private String userName;
    private String title;
    private String content;
    private String address;
    private String createdAt;
    private String updatedAt;
    private String isUpdated;
    private Integer likeCnt;
}
