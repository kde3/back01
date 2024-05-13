package com.fiveis.leasemates.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JoinDTO {
    private String id;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
}
