package com.fiveis.leasemates.domain.vo;

import lombok.*;

@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@EqualsAndHashCode
public class LikeVO {
    private String userNo;
    private Long postNo;
}
