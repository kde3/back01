package com.fiveis.leasemates.domain.vo;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class FileVO {
    private Long fileNo;
    private Long postNo;
    private String filePath;
}
