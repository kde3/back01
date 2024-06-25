package com.fiveis.leasemates.controller;

import com.fiveis.leasemates.domain.PageBlockDTO;
import com.fiveis.leasemates.domain.Pageable;
import com.fiveis.leasemates.domain.dto.sharehouse.SharehousePostDTO;
import com.fiveis.leasemates.domain.dto.sharehouse.SharehousePostDetailDTO;
import com.fiveis.leasemates.domain.vo.LikeVO;
import com.fiveis.leasemates.security.CustomUserDetails;
import com.fiveis.leasemates.service.SharehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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

    /**
     * 게시글 상세페이지
     * @return
     */
    @GetMapping("/{postNo}")
    public String postDetailView(@PathVariable("postNo") Long postNo,
                                 Model model) {

        String userNo = "anonymousUser";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!userNo.equals(principal)) {
            CustomUserDetails customUserDetails = (CustomUserDetails) principal;
            userNo = customUserDetails.getUserVO().getUserNo();
        }

        SharehousePostDetailDTO sharehousePostDetailDTO = sharehouseService.getPostDetail(postNo, userNo);
        model.addAttribute("sharehousePostDetailDTO", sharehousePostDetailDTO);

//        board.setContent(content.replaceAll("\n", "<br>"));

        return "sharehouse/detail";
    }

    /**
     *  좋아요 하기
     */
    @PostMapping("/{postNo}/like")
    public String createLike(@PathVariable("postNo") Long postNo) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails customUserDetails = (CustomUserDetails) principal;
        String userNo = customUserDetails.getUserVO().getUserNo();

        LikeVO likeVO = LikeVO.builder()
                .postNo(postNo)
                .userNo(userNo)
                .build();

        sharehouseService.createLike(likeVO);

        log.info("[좋아요]");

        return "redirect:/sharehouse/" + postNo;
    }

    /**
     *  좋아요 취소
     */
    @PostMapping("/{postNo}/unlike")
    public String deleteLike(@PathVariable("postNo") Long postNo) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails customUserDetails = (CustomUserDetails) principal;
        String userNo = customUserDetails.getUserVO().getUserNo();

        LikeVO likeVO = LikeVO.builder()
                .postNo(postNo)
                .userNo(userNo)
                .build();

        sharehouseService.deleteLike(likeVO);

        log.info("[좋아요 취소]");

        return "redirect:/sharehouse/" + postNo;
    }
}
