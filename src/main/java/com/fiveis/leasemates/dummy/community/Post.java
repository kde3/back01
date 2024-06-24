package com.fiveis.leasemates.dummy.community;

import com.fiveis.leasemates.domain.vo.CmtVO;
import com.fiveis.leasemates.domain.vo.PostVO;
import com.fiveis.leasemates.domain.vo.sharehouse.SharehousePostVO;
import com.fiveis.leasemates.repository.CommunityRepository;
import com.fiveis.leasemates.repository.SharehouseRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Post {
    private final CommunityRepository communityRepository;
    private final SharehouseRepository sharehouseRepository;

    @PostConstruct
    public void createDummy() {
//        커뮤니티 데이터 생성
        for (int i = 0; i < 12; i++) {
            PostVO postVO = PostVO.builder()
                    .userNo("f00586b2-e0d0-4a44-a05b-95177a752350")
                    .title("테스트 " + i)
                    .content("게시글 컨텐츠 " + i)
                    .build();

            communityRepository.createDummyPost(postVO);
        }

        for (int i = 12; i < 124; i++) {
            PostVO postVO = PostVO.builder()
                    .userNo("06c901bd-a6a9-4c65-a802-86222b651e4a")
                    .title("테스트 " + i)
                    .content("게시글 컨텐츠 " + i)
                    .build();

            communityRepository.createDummyPost(postVO);
        }

//        댓글 데이터 생성
        for (int i = 0; i < 77; i++) {
            CmtVO cmtVO = CmtVO.builder()
                    .postNo(1L)
                    .userNo("f00586b2-e0d0-4a44-a05b-95177a752350")
                    .content("게시글 컨텐츠 " + i)
                    .build();

            communityRepository.createDummyCmt(cmtVO);
        }

//        sharehouse 데이터 생성
        for (int i = 0; i < 30; i++) {
            SharehousePostVO sharehousePostVO = SharehousePostVO.builder()
                    .userNo("f00586b2-e0d0-4a44-a05b-95177a752350")
                    .title("테스트 " + i)
                    .content("게시글 컨텐츠 " + i)
                    .build();

            sharehouseRepository.createDummy(sharehousePostVO);
        }

    }
}
