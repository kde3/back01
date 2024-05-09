package com.fiveis.leasemates.domain.vo;

import lombok.*;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@EqualsAndHashCode
public class UserVO {
    private String userNo;
    private String id;
    private String password;
    private String role;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate createdAt;
}
