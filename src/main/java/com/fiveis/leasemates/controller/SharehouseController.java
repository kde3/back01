package com.fiveis.leasemates.controller;

import com.fiveis.leasemates.domain.PageBlockDTO;
import com.fiveis.leasemates.domain.Pageable;
import com.fiveis.leasemates.domain.dto.sharehouse.SharehousePostDTO;
import com.fiveis.leasemates.domain.dto.sharehouse.SharehousePostDetailDTO;
import com.fiveis.leasemates.domain.vo.FileVO;
import com.fiveis.leasemates.domain.vo.LikeVO;
import com.fiveis.leasemates.domain.vo.PostVO;
import com.fiveis.leasemates.domain.vo.sharehouse.SharehousePostVO;
import com.fiveis.leasemates.security.CustomUserDetails;
import com.fiveis.leasemates.service.FileService;
import com.fiveis.leasemates.service.SharehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/sharehouse")
public class SharehouseController {
    private final SharehouseService sharehouseService;
    private final FileService fileService;

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

    /**
     * 게시글 작성
     * /sharehouse/create
     */
    @GetMapping("/create")
    public  String createPost(@RequestParam(value = "postNo", required = false) final Long postNo,
                              Model model){

        if(postNo != null){
            Optional<SharehousePostVO> postOptional = sharehouseService.findById(postNo);
            if(postOptional.isPresent()){
                SharehousePostVO post = postOptional.get();
                model.addAttribute("post", post);
                model.addAttribute("address", post.getAddress());
            }
        }
        return "sharehouse/create";
    }


    @PostMapping("/create")
    public String createPost(@ModelAttribute SharehousePostVO sharehousePostVO, @ModelAttribute FileVO fileVO,
                             @RequestParam("files") List<MultipartFile> files,
                             @ModelAttribute PostVO postVO,
                             RedirectAttributes redirectAttributes) throws IOException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails customUserDetails = (CustomUserDetails) principal;
        String userNo = customUserDetails.getUserVO().getUserNo();

        // likeCnt가 null이면 기본값을 설정
        if (sharehousePostVO.getLikeCnt() == null) {
            sharehousePostVO.setLikeCnt(0); // 기본값 0으로 설정
        }

        SharehousePostVO newPost = SharehousePostVO.builder()
                .postNo(sharehousePostVO.getPostNo())
                .title(sharehousePostVO.getTitle())
                .userNo(userNo)
                .content(sharehousePostVO.getContent())
                .address(sharehousePostVO.getAddress())
                .build();

        Long postNo = sharehouseService.createPost(newPost, files);

        // 파일 업로드 처리
        for (MultipartFile file : files) {
            fileService.uploadFile(file, fileVO, postVO);
        }


        redirectAttributes.addAttribute("postNo", postNo);

        return "redirect:/sharehouse/" + postNo;
    }




    /**
     * 게시글 수정
     * /sharehouse/{postNo}/update
     */
    @GetMapping("/{postNo}/update")
    public String editPost(@PathVariable Long postNo, Model model){
        Optional<SharehousePostVO> postOptional = sharehouseService.findById(postNo);
        if(postOptional.isPresent()){
            SharehousePostVO post = postOptional.get();
            model.addAttribute("post", post);
        }
        return "sharehouse/edit";
    }


    @PostMapping("/{postNo}/update")
    public String updatePost(@PathVariable Long postNo,
                             SharehousePostVO sharehousePostVO,
                             @RequestPart(value="file",required = false)  List<MultipartFile> files,
                             FileVO fileVO
    ) throws IOException {
        // 게시글 업데이트
        sharehouseService.updatePost(sharehousePostVO, files, fileVO);

        // 기존 파일 삭제
        fileService.deleteFile(fileVO.getFileNo());


        String userHome = System.getProperty("user.home");
        String uploadDir = userHome + "/uploads";

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;

                try {
                    String fileName = UUID.randomUUID().toString().replaceAll("-", "") +
                            "-" + file.getOriginalFilename();
                    String filePath = uploadDir + "/" + fileName;

                    FileVO updateFile = FileVO.builder()
                            .fileNo(fileVO.getFileNo())
                            .postNo(sharehousePostVO.getPostNo())
                            .filePath(filePath)
                            .build();
                    System.out.println(updateFile);
                    fileService.insertFile(fileVO);

                    Path path = Paths.get(filePath);
                    Files.copy(file.getInputStream(), path);

                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("파일 저장에 실패했습니다", e);
                }
            }
        } else {
            System.out.println("파일이 없습니다");
        }
        return "redirect:/sharehouse/" + postNo ;
    }

    /**
     * 게시글 삭제
     * /sharehouse/{postNo}/delete
     */
    @GetMapping("/{postNo}/delete")
    public String deletePost(@PathVariable("postNo") Long postNo){
        sharehouseService.deletePost(postNo);
        return "redirect:/sharehouse/";
    }

}
