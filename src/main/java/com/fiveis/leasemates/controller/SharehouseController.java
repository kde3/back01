package com.fiveis.leasemates.controller;

import com.fiveis.leasemates.domain.PageBlockDTO;
import com.fiveis.leasemates.domain.Pageable;
import com.fiveis.leasemates.domain.dto.community.PostDTO;
import com.fiveis.leasemates.domain.dto.sharehouse.SharehousePostDTO;
import com.fiveis.leasemates.service.SharehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sharehouse")
public class SharehouseController {
    private final SharehouseService sharehouseService;

    @GetMapping("/")
    public String mainView(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "12") int size,
                           Model model) {

        Pageable pageable = new Pageable();
        pageable.setPage(page);
        pageable.setPageSize(size);

        List<SharehousePostDTO> sharehousePostDTOList = sharehouseService.postPagination(pageable);
        model.addAttribute("sharehousePostDTOList", sharehousePostDTOList);

        PageBlockDTO pageBlockDTO = sharehouseService.postPaginationBlock(5, pageable);
        model.addAttribute("pageBlockDTO", pageBlockDTO);

        return "sharehouse/main";
    }
}
