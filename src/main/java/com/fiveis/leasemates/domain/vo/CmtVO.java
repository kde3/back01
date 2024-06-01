package com.fiveis.leasemates.domain.vo;

import lombok.*;

@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@EqualsAndHashCode
public class CmtVO {
    private Long cmtNo;
    private Long postNo;
    private String userNo;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String isUpdated;
}
