package com.fiveis.leasemates.controller;

import com.fiveis.leasemates.domain.PageBlockDTO;
import com.fiveis.leasemates.domain.Pageable;
import com.fiveis.leasemates.domain.dto.community.PostDTO;
import com.fiveis.leasemates.domain.dto.user.JoinDTO;
import com.fiveis.leasemates.domain.dto.user.UpdateDTO;
import com.fiveis.leasemates.domain.dto.user.UpdatePwDTO;
import com.fiveis.leasemates.security.CustomUserDetails;
import com.fiveis.leasemates.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public void login(@RequestParam(defaultValue = "false") boolean error,
                      Model model) {

        if (error) model.addAttribute("error", "아이디나 비밀번호가 잘못되었습니다.");
    }

    @GetMapping("/join")
    public void join() {
    }

    @GetMapping("/join_terms")
    public void joinTerms() {
    }

    @GetMapping("/info")
    public void userInfoView() {
    }

    @GetMapping("/info_change")
    public void userInfoChangeView() {
    }

    @GetMapping("/pw_change")
    public void updateUserPw() {
    }

    @GetMapping("/myposts")
    public void myPostsView(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {
        Pageable pageable = new Pageable();
        pageable.setPage(page);
        pageable.setPageSize(size);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails customUserDetails = (CustomUserDetails) principal;
        String userNo = customUserDetails.getUserVO().getUserNo();

        List<PostDTO> postDTOList = userService.userPostPagination(userNo, pageable);
        model.addAttribute("postDTOList", postDTOList);
        for(PostDTO post : postDTOList) log.info("post = " + post);

        PageBlockDTO pageBlockDTO = userService.userPostPaginationBlock(5, pageable, userNo);
        model.addAttribute("pageBlockDTO", pageBlockDTO);
        log.info("pageBlockDTO 값: "+ pageBlockDTO);
    }

    /**
     * 회원가입
     */
    @PostMapping("/join")
    String join(JoinDTO joinDTO) {
        Boolean result = userService.join(joinDTO);
        if(result) return "redirect:/user/login";

        return "/user/join";
    }

    @PostMapping("/info_change")
    public String updateUserinfo(UpdateDTO updateDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails customUserDetails = (CustomUserDetails) principal;
        String userNo = customUserDetails.getUserVO().getUserNo();
        userService.updateUserInfo(userNo, updateDTO);

        log.info("[회원정보 변경 완료]");

        return "redirect:/user/info_change";
    }

    @PostMapping("/pw_change")
    public ResponseEntity<String> updateUserPw(@RequestBody UpdatePwDTO updatePwDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails customUserDetails = (CustomUserDetails) principal;
        String userNo = customUserDetails.getUserVO().getUserNo();
        String userId = customUserDetails.getUsername();
        String userPw = customUserDetails.getUserVO().getPassword();

        if( userService.updateUserPw(userNo, userId, userPw, updatePwDTO)) {
            log.info("비밀번호 수정완료");
            return ResponseEntity.ok("Password changed successfully");
        } else {
            log.info("비밀번호 다름");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect current password");
        }
    }

    /**
     * 로그아웃
     * @return
     */
    @PostMapping("/logout")
    public String logout() {
        return "redirect:/community/";
    }
}
