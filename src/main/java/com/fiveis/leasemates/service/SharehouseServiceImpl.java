package com.fiveis.leasemates.service;

import com.fiveis.leasemates.domain.PageBlockDTO;
import com.fiveis.leasemates.domain.Pageable;
import com.fiveis.leasemates.domain.dto.sharehouse.SharehousePostDTO;
import com.fiveis.leasemates.domain.dto.sharehouse.SharehousePostDetailDTO;
import com.fiveis.leasemates.domain.vo.FileVO;
import com.fiveis.leasemates.domain.vo.LikeVO;
import com.fiveis.leasemates.domain.vo.sharehouse.SharehousePostVO;
import com.fiveis.leasemates.repository.FileRepository;
import com.fiveis.leasemates.repository.SharehouseRepository;
import com.fiveis.leasemates.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SharehouseServiceImpl implements SharehouseService {
    private final SharehouseRepository sharehouseRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    @Value("${file.dir}")
    private String fileDir;

    @Override
    public List<SharehousePostDTO> postPagination(Pageable pageable) {
        List<SharehousePostDTO> sharehousePostDTOList = sharehouseRepository.postPagination(pageable);

        // 닉네임 담기
        for(SharehousePostDTO sharehousePostDTO : sharehousePostDTOList) {
            sharehousePostDTO.setUserName(userRepository.findNameByUserNo(sharehousePostDTO.getUserNo()));
        }

        return sharehousePostDTOList;
    }

    @Override
    public PageBlockDTO postPaginationBlock(int blockSize, Pageable pageable) {
        int postTotal = sharehouseRepository.findPostAllCount();
        int totalPages = (int) Math.ceil(postTotal / (double) pageable.getPageSize());  // 전체 버튼 개수

        return new PageBlockDTO(blockSize, totalPages, pageable);
    }

    @Override
    public SharehousePostDetailDTO getPostDetail(Long postNo, String userNo) {
        SharehousePostVO foundPost = sharehouseRepository.findPostById(postNo)
                .orElseThrow(() -> new IllegalStateException("게시물이 존재하지 않습니다."));

        String username = userRepository.findNameByUserNo(foundPost.getUserNo());

        // 유저가 게시물에 좋아요했는지 확인하기
        LikeVO likeVO = LikeVO.builder()
                .postNo(postNo)
                .userNo(userNo).build();
        int checkLike = sharehouseRepository.findLikeById(likeVO);

        SharehousePostDetailDTO sharehousePostDetailDTO = SharehousePostDetailDTO.from(foundPost);
        sharehousePostDetailDTO.setUserName(username);
        sharehousePostDetailDTO.setCheckLike(checkLike);

        return sharehousePostDetailDTO;
    }

    /**
     * 좋아요 하기
     * @param likeVO
     */
    @Override
    public void createLike(LikeVO likeVO) {
        sharehouseRepository.createLike(likeVO);
        sharehouseRepository.updateLikeCnt(likeVO.getPostNo());
    }

    /**
     * 좋아요 취소
     * @param likeVO
     */
    @Override
    public void deleteLike(LikeVO likeVO) {
        sharehouseRepository.deleteLikeById(likeVO);
        sharehouseRepository.updateLikeCnt(likeVO.getPostNo());
    }

    @Override
    public Long createPost(SharehousePostVO sharehousePostVO, List<MultipartFile> files) {
        Long postNo = sharehouseRepository.getPostNo();

        SharehousePostVO sharehousePost = SharehousePostVO.builder()
                .postNo(postNo)
                .userNo(sharehousePostVO.getUserNo())
                .title(sharehousePostVO.getTitle())
                .content(sharehousePostVO.getContent())
                .build();

        System.out.println("sharehousePost : " + sharehousePostVO );

        if (sharehousePost.getUserNo() == null) {
            throw new IllegalArgumentException("User number cannot be null");
        }
        sharehouseRepository.createPost(sharehousePost);

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

    @Override
    public void updatePost(SharehousePostVO sharehousePostVO, List<MultipartFile> files, FileVO fileVO) {
        //기존 파일 삭제
        fileRepository.deleteFileById(sharehousePostVO.getPostNo());
        //게시글 업데이트
        sharehouseRepository.updatePost(sharehousePostVO);

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
                            .postNo(sharehousePostVO.getPostNo())
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

    @Override
    public void deletePost(long postNo) {
        sharehouseRepository.deletePostById(postNo);
    }

    @Override
    public Optional<SharehousePostVO> findById(Long postNO) {
        return sharehouseRepository.findPostById(postNO);
    }

}
