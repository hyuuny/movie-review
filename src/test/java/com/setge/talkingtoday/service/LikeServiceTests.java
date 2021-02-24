package com.setge.talkingtoday.service;

import com.setge.talkingtoday.dto.LikeDTO;
import com.setge.talkingtoday.entity.Like;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LikeServiceTests {

    @Autowired
    private LikeService likeService;

    @Test
    public void 좋아요_한다() {
        LikeDTO likeDTO = LikeDTO.builder()
                .mid(5L)
                .mno(7L)
                .build();

        likeService.register(likeDTO);
    }

    @Test
    public void 좋아요_취소한다() {
        likeService.remove(2L, 22L);
    }

    @Test
    public void 페이지_조회하면_좋아요수_얻는다() {
        int likeCount = likeService.getLikeCount(22L);
        System.out.println("likeCount : " + likeCount);
    }

}
