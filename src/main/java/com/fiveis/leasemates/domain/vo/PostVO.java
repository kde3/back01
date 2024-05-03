package com.fiveis.leasemates.domain.vo;

import lombok.*;

import java.time.LocalDateTime;

@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@EqualsAndHashCode
public class PostVO {
    private Long postNo;
    private String userNo;
    private String title;
    private String content;
    private LocalDateTime updatedAt;
    private String isUpdated;
    private int likeCnt;
}
