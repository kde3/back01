package com.fiveis.leasemates.domain.vo;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@EqualsAndHashCode
public class PostVO {
    private Long postNo;
    private String userNo;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String isUpdated;
    private Integer likeCnt = 0;
    private Integer CmtCnt = 0;
}
