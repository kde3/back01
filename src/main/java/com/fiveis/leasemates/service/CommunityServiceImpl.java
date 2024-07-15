package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.PageBlockDTO;
import com.fiveis.leasemates.domain.Pageable;
import com.fiveis.leasemates.domain.dto.community.CmtDTO;
import com.fiveis.leasemates.domain.dto.community.PostDTO;
import com.fiveis.leasemates.domain.dto.community.PostDetailDTO;
import com.fiveis.leasemates.domain.vo.*;
import com.fiveis.leasemates.repository.CommunityRepository;
import com.fiveis.leasemates.repository.FileRepository;
import com.fiveis.leasemates.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    @Value("${file.dir}")
    private String fileDir;

    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    /**
     * @param postNo
     * 커뮤니티 게시물 상세 페이지 보여주기(+ 댓글 목록도)
     */
    @Override
    public PostDetailDTO getPostDetail(Long postNo, String userNo, Pageable pageable) {
        PostVO foundPost = communityRepository.findPostById(postNo)
                .orElseThrow(()->new IllegalStateException("게시물이 존재하지 않습니다."));

        String username = userRepository.findNameByUserNo(foundPost.getUserNo());

        // 유저가 게시물에 좋아요했는지 확인하기
        LikeVO likeVO = LikeVO.builder()
                .postNo(postNo)
                .userNo(userNo).build();
        int checkLike = communityRepository.findLikeById(likeVO);

        PostDetailDTO postDetailDTO = PostDetailDTO.from(foundPost);
        postDetailDTO.setUserName(username);
        postDetailDTO.setCheckLike(checkLike);

        List<CmtDTO> cmtDTOs = null;
        List<CmtVO> cmtVOs = communityRepository.cmtPagination(postNo, pageable);

        if (cmtVOs != null) {
            cmtDTOs = cmtVOs.stream()
                    .map(CmtDTO::from)
                    .toList();

            // 닉네임 담기
            for(CmtDTO cmtDTO : cmtDTOs) {
                cmtDTO.setUserName(userRepository.findNameByUserNo(cmtDTO.getUserNo()));
            }
        }

        postDetailDTO.setCmtDTOList(cmtDTOs);

        return postDetailDTO;
    }

    @Override
    public PageBlockDTO cmtPaginationBlock(Long postNo, int blockSize, Pageable pageable) {
        int cmtTotal = communityRepository.findCmtAllCount(postNo);
        int totalPages = (int) Math.ceil(cmtTotal / (double) pageable.getPageSize());  // 전체 버튼 개수

        return new PageBlockDTO(blockSize, totalPages, pageable);
    }

    /**
     * 커뮤니티 댓글 목록 보기
     */
    @Override
    public List<CmtDTO> findCmtAll(Long postNo) {
        List<CmtDTO> cmtDTOs = null;

        List<CmtVO> foundCmts = communityRepository.findCmtAll(postNo);

        if (foundCmts != null) {
            cmtDTOs = foundCmts.stream()
                    .map(CmtDTO::from)
                    .toList();

            // 닉네임 담기
            for(CmtDTO cmtDTO : cmtDTOs) {
                cmtDTO.setUserName(userRepository.findNameByUserNo(cmtDTO.getUserNo()));
            }
        }

        return cmtDTOs;
    }

    /**
     * 커뮤니티 댓글 추가
     * @param cmtVO
     * @return cmtNo : 생성된 댓글의 번호
     */
    @Override
    public List<CmtVO> createCmt(CmtVO cmtVO) {
        Long cmtNo = communityRepository.getCmtNo();

        CmtVO newCmt = CmtVO.builder()
                .cmtNo(cmtNo)
                .postNo(cmtVO.getPostNo())
                .userNo(cmtVO.getUserNo())
                .content(cmtVO.getContent())
                .build();

        communityRepository.createCmt(newCmt);
        List<CmtVO> cmtAll = communityRepository.findCmtAll(newCmt.getPostNo());

        return cmtAll;
    }

    /**
     * 커뮤니티 댓글 수정
     * @param cmtVO
     */
    @Override
    public void updateCmt(Long postNo, CmtVO cmtVO) {
        CmtVO updatedCmtVO = CmtVO.builder()
                .cmtNo(cmtVO.getCmtNo())
                .content(cmtVO.getContent())
                .build();

        communityRepository.updateCmt(updatedCmtVO);

        //댓글 개수 업데이트
        communityRepository.updateCmtCnt(postNo);
    }

    /**
     * 커뮤니티 댓글 삭제
     * @param cmtNo
     */
    @Override
    public void deleteCmt(Long postNo, Long cmtNo) {
        communityRepository.deleteCmtById(cmtNo);

        //댓글 개수 업데이트
        communityRepository.updateCmtCnt(postNo);
    }

    /**
     * 좋아요 하기
     * @param likeVO
     */
    @Override
    public void createLike(LikeVO likeVO) {
        communityRepository.createLike(likeVO);
        communityRepository.updateLikeCnt(likeVO.getPostNo());
    }

    /**
     * 좋아요 취소
     * @param likeVO
     */
    @Override
    public void deleteLike(LikeVO likeVO) {
        communityRepository.deleteLikeById(likeVO);
        communityRepository.updateLikeCnt(likeVO.getPostNo());
    }

    /**
     * 게시글 작성
     * @param postVO
     */
    @Override
    public Long createPost(PostVO postVO, List<MultipartFile> files) {
        Long postNo = communityRepository.getPostNo();

        PostVO post = PostVO.builder()
                .postNo(postNo)
                .userNo(postVO.getUserNo())
                .title(postVO.getTitle())
                .content(postVO.getContent())
                .build();

        System.out.println("postVO" + post);

        if (post.getUserNo() == null) {
            throw new IllegalArgumentException("User number cannot be null");
        }
        communityRepository.createPost(post);

        String userHome = System.getProperty("user.home");
        String uploadDir = userHome + "/uploads";

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        if (files != null) {
            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;

                try {
                    String fileName = UUID.randomUUID().toString().replaceAll("-", "") +
                            "-" + file.getOriginalFilename();
                    String filePath = uploadDir + "/" + fileName;

                    FileVO fileVO = FileVO.builder()
                            .fileNo(fileRepository.getFileNo())
                            .postNo(postNo)
                            .filePath(filePath)
                            .build();
                    System.out.println(fileVO);
                    fileRepository.insertFile(fileVO);

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

        return postNo;
    }



    /**
     * 게시글 목록 보기
     */
    @Override
    public List<PostVO> findPostAll() {
        return communityRepository.findPostAll();
    }


    @Override
    public List<PostDTO> postPagination(Pageable pageable) {
        List<PostDTO> postDTOList = communityRepository.postPagination(pageable);

        // 닉네임 담기
        for(PostDTO postDTO : postDTOList) {
            postDTO.setUserName(userRepository.findNameByUserNo(postDTO.getUserNo()));
        }

        return postDTOList;
    }

    @Override
    public PageBlockDTO postPaginationBlock(int blockSize, Pageable pageable) {
        int postTotal = communityRepository.findPostAllCount();
        int totalPages = (int) Math.ceil(postTotal / (double) pageable.getPageSize());  // 전체 버튼 개수

        return new PageBlockDTO(blockSize, totalPages, pageable);
    }

    /**
     * 게시글 수정
     * @param postVO
     */
    @Override
    public void updatePost(PostVO postVO, List<MultipartFile> files, FileVO fileVO) {
        //기존 파일 삭제
        fileRepository.deleteFileById(postVO.getPostNo());
        //게시글 업데이트
        communityRepository.updatePost(postVO);

        String userHome = System.getProperty("user.home");
        String uploadDir = userHome + "/uploads";

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        if (files != null) {
            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;

                try {
                    String fileName = UUID.randomUUID().toString().replaceAll("-", "") +
                            "-" + file.getOriginalFilename();
                    String filePath = uploadDir + "/" + fileName;

                    FileVO updateFile = FileVO.builder()
                            .fileNo(fileRepository.getFileNo())
                            .postNo(postVO.getPostNo())
                            .filePath(filePath)
                            .build();
                    System.out.println(updateFile);
                    fileRepository.insertFile(updateFile);

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
    }

    /**
     * 게시글 삭제
     * @param postNo
     */
    @Override
    public void deletePost(long postNo) {
        communityRepository.deletePostById(postNo);
    }

    /**
     * 게시글 게시글 찾기
     * @param postNO
     */
    @Override
    public Optional<PostVO> findById(Long postNO) {
        return communityRepository.findPostById(postNO);
    }
}
