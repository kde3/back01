package com.fiveis.leasemates.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @ToString
public class PageBlockDTO {
    private int blockSize;      // 페이지 한 블록당 크기
    private int currentPage;    // 현재 페이지 번호
    private int startPage;      // 페이지 블록의 시작 번호
    private int endPage;        // 페이지 블록의 끝 번호
    private int lastPage;       // 전체 페이지 중 마지막 페이지 번호

    public PageBlockDTO(int blockSize, int totalPages, Pageable pageable) {
        this.blockSize = blockSize;
        this.currentPage = pageable.getPage();

        this.endPage = (int) Math.ceil( currentPage / (double) blockSize ) * blockSize;
        this.startPage = endPage - blockSize + 1;
        this.lastPage = totalPages == 0 ? 1 : totalPages;

        this.endPage = Math.min(endPage, lastPage);
    }

    public boolean hasNextBlock(){
        return endPage < lastPage;
    }

    public boolean hasPrevBlock(){
        return startPage > 1;
    }
}
