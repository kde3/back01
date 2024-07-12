package com.fiveis.leasemates.domain.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdatePwDTO {
    private String password;
    private String newPassword;
}
