package com.fiveis.leasemates.domain.vo.sharehouse;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class SharehousePostVO {
    private Long postNo;
    private String userNo;
    private String title;
    private String content;
    private String address;
    private String createdAt;
    private String updatedAt;
    private String isUpdated;
    private Integer likeCnt = 0;
}
