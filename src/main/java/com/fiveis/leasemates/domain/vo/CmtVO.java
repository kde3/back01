package com.fiveis.leasemates.domain.vo;

import lombok.*;
import java.time.LocalDateTime;

@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@EqualsAndHashCode
public class CmtVO {
    private Long cmtNo;
    private Long postNo;
    private String userNo;
    private String content;
    private LocalDateTime updatedAt;
    private String isUpdated;
}
