package com.fiveis.leasemates.domain.vo;

import lombok.*;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@EqualsAndHashCode
public class UserVO {
    private String userNo;          //임시로 String. UUID로 타입 바꿔야.
    private String id;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate createdAt;
}
