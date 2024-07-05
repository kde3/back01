package com.fiveis.leasemates.controller;

import com.fiveis.leasemates.domain.PageBlockDTO;
import com.fiveis.leasemates.domain.Pageable;
import com.fiveis.leasemates.domain.dto.community.CmtDTO;
import com.fiveis.leasemates.domain.dto.community.PostDTO;
import com.fiveis.leasemates.domain.dto.community.PostDetailDTO;
import com.fiveis.leasemates.domain.vo.CmtVO;
import com.fiveis.leasemates.domain.vo.FileVO;
import com.fiveis.leasemates.domain.vo.LikeVO;
import com.fiveis.leasemates.domain.vo.PostVO;
import com.fiveis.leasemates.security.CustomUserDetails;
import com.fiveis.leasemates.service.CommunityService;
import com.fiveis.leasemates.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
@Slf4j
public class CommunityController {
    private final CommunityService communityService;
    private final FileService fileService;

    /**
     * 메인 페이지(게시물 목록 페이지)
     * @param model
     * @return
     */
    @GetMapping("/")
    public String mainView(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "10") int size,
                           Model model) {

        Pageable pageable = new Pageable();
        pageable.setPage(page);
        pageable.setPageSize(size);

        List<PostDTO> postDTOList = communityService.postPagination(pageable);
        for(PostDTO post : postDTOList) log.info("post = " + post);
        model.addAttribute("postDTOList", postDTOList);

        PageBlockDTO pageBlockDTO = communityService.postPaginationBlock(5, pageable);
        model.addAttribute("pageBlockDTO", pageBlockDTO);
        log.info("pageBlockDTO 값: "+ pageBlockDTO);

        return "community/main";
    }


    /**
     * 게시글 상세페이지
     * @return
     */
    @GetMapping("/{postNo}")
    public String postDetailView(@PathVariable("postNo") Long postNo,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 Model model) {

        Pageable pageable = new Pageable();
        pageable.setPage(page);
        pageable.setPageSize(size);

        String userNo = "anonymousUser";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!userNo.equals(principal)) {
            CustomUserDetails customUserDetails = (CustomUserDetails) principal;
            userNo = customUserDetails.getUserVO().getUserNo();
        }

        PostDetailDTO postDetailDTO = communityService.getPostDetail(postNo, userNo, pageable);
        model.addAttribute("postDetailDTO", postDetailDTO);

        PageBlockDTO pageBlockDTO = communityService.cmtPaginationBlock(postNo, 5, pageable);
        model.addAttribute("pageBlockDTO", pageBlockDTO);

//        board.setContent(content.replaceAll("\n", "<br>"));

        return "community/detail";
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

        communityService.createLike(likeVO);

        log.info("[좋아요]");

        return "redirect:/community/" + postNo;
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

        communityService.deleteLike(likeVO);

        log.info("[좋아요 취소]");

        return "redirect:/community/" + postNo;
    }

    /**
     * 댓글 추가
     */
    @PostMapping("/{postNo}/cmt/create")
    public String createCmt(@PathVariable("postNo") Long postNo, String cmt) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails customUserDetails = (CustomUserDetails) principal;
        String userNo = customUserDetails.getUserVO().getUserNo();

        log.info("댓글 : " + cmt);

        CmtVO newCmt = CmtVO.builder()
                .postNo(postNo)
                .userNo(userNo)
                .content(cmt)
                .build();

        List<CmtVO> cmtVOs = communityService.createCmt(newCmt);

        return "redirect:/community/" + postNo;
    }


    /**
     * 댓글 수정
     */
    @PostMapping("/{postNo}/cmt/update")
    public String updateCmt(@PathVariable("postNo") Long postNo,
                                 @RequestParam(value = "cmtNo", required = true) Long cmtNo,
                                 String cmtUpdate) {

        CmtVO updatedCmt = CmtVO.builder()
                .cmtNo(cmtNo)
                .content(cmtUpdate)
                .build();

        communityService.updateCmt(postNo, updatedCmt);

        log.info("[댓글 수정]");

        return "redirect:/community/" + postNo;
    }

    /**
     * 댓글 삭제
     */
    @GetMapping("/{postNo}/cmt/delete")
    public String deleteCmt(@PathVariable("postNo") Long postNo,
                @RequestParam(value = "cmtNo", required = true) Long cmtNo) {
        communityService.deleteCmt(postNo, cmtNo);

        log.info("[댓글 삭제]");

        return "redirect:/community/" + postNo;
    }


    /**
     * 게시글 작성
     * /community/create
     */
    @GetMapping("/create")
    public  String createPost(@RequestParam(value = "postNo", required = false) final Long postNo, Model model){
        if(postNo != null){
            Optional<PostVO> postOptional = communityService.findById(postNo);
            if(postOptional.isPresent()){
                PostVO post = postOptional.get();

                model.addAttribute("post", post);
            }
        }
        return "community/create";
    }


    @PostMapping("/create")
    public String createPost(@ModelAttribute PostVO postVO, @ModelAttribute FileVO fileVO,
                             @RequestParam("files") List<MultipartFile> files,
                             RedirectAttributes redirectAttributes) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails customUserDetails = (CustomUserDetails) principal;
        String userNo = customUserDetails.getUserVO().getUserNo();

        // likeCnt가 null이면 기본값을 설정
        if (postVO.getLikeCnt() == null) {
            postVO.setLikeCnt(0); // 기본값 0으로 설정
        }

        PostVO newPost = PostVO.builder()
                .title(postVO.getTitle())
                .userNo(userNo)
                .content(postVO.getContent())
                .build();


        Long postNo = communityService.createPost(newPost, files);

        // 파일 업로드 처리
        for (MultipartFile file : files) {
            fileService.uploadFile(file, fileVO, postVO);
        }

        redirectAttributes.addAttribute("postNo", postNo);

        return "redirect:/community/" + postNo;
    }




    /**
     * 게시글 수정
     * /community/{postNo}/update
     */
    @GetMapping("/{postNo}/update")
    public String editPost(@PathVariable Long postNo, Model model){
        Optional<PostVO> postOptional = communityService.findById(postNo);
        if(postOptional.isPresent()){
            PostVO post = postOptional.get();
            model.addAttribute("post", post);
        }
        return "community/edit";
    }


    @PostMapping("/{postNo}/update")
    public String updatePost(@PathVariable Long postNo,
                             PostVO postVO,
                             @RequestPart(value="file",required = false)  List<MultipartFile> files,
                             FileVO fileVO
    ) throws IOException {
        // 게시글 업데이트
        communityService.updatePost(postVO, files, fileVO);

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
                            .postNo(postVO.getPostNo())
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
        return "redirect:/community/" + postNo ;
    }

    /**
     * 게시글 삭제
     * /community/{postNo}/delete
     */
    @GetMapping("/{postNo}/delete")
    public String deletePost(@PathVariable("postNo") Long postNo){
        communityService.deletePost(postNo);
        return "redirect:/community/";
    }


}
