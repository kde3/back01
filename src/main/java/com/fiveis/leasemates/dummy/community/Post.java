package com.fiveis.leasemates.dummy.community;

import com.fiveis.leasemates.domain.vo.CmtVO;
import com.fiveis.leasemates.domain.vo.PostVO;
import com.fiveis.leasemates.repository.CommunityRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class Post {
    private final CommunityRepository communityRepository;

    @PostConstruct
    public void createDummy() {
        for (int i = 0; i < 12; i++) {
            PostVO postVO = PostVO.builder()
                    .userNo("f00586b2-e0d0-4a44-a05b-95177a752350")
                    .title("테스트 " + i)
                    .content("게시글 컨텐츠 " + i)
                    .build();

            communityRepository.createDummyPost(postVO);
        }

        for (int i = 0; i < 77; i++) {
            CmtVO cmtVO = CmtVO.builder()
                    .postNo(1L)
                    .userNo("f00586b2-e0d0-4a44-a05b-95177a752350")
                    .content("게시글 컨텐츠 " + i)
                    .build();

            communityRepository.createDummyCmt(cmtVO);
        }
    }
}
