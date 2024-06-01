package com.fiveis.leasemates.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Pageable {
    private int page;       //원래는 0부터 시작이나 여기서는 1부터 시작
    private int pageSize;
}
