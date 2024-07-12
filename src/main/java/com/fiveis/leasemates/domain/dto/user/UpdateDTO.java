package com.fiveis.leasemates.domain.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateDTO {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
}
