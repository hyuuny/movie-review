package com.setge.talkingtoday.repository;

import com.setge.talkingtoday.entity.Like;
import com.setge.talkingtoday.entity.Member;
import com.setge.talkingtoday.entity.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class LikeRepositoryTests {

    @Autowired
    private LikeRepository likeRepository;


    @Test
    public void 좋아요_추가한다() {

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Long mno = (long)(Math.random() * 15) + 1;
            System.out.println("ran : " + mno);
            Long mid = (long)(Math.random() * 3) + 1;
            Movie movie = Movie.builder().mno(mno).build();
            Member member = Member.builder().mid(mid).build();

            Like like = Like.builder()
                    .member(member)
                    .movie(movie)
                    .build();

            likeRepository.save(like);
        });
    }

    @Test
    public void 게시글로부터_좋아요얻는다() {
        Like like = likeRepository.getLibByMidAndMno(2L, 16L);
        System.out.println("list ; " + like.getLid());

    }

    @Commit
    @Transactional
    @Test
    public void 좋아요_삭제한다() {

        Like like = Like.builder()
                .lid(4L)
                .member(Member.builder().mid(3L).build())
                .movie(Movie.builder().mno(16L).build())
                .build();

        likeRepository.delete(like);
    }

    @Commit
    @Transactional
    @Test
    public void 좋아요_삭제한다2() {
        likeRepository.deleteByMidAndMno(2L, 15L);
    }

    @Test
    public void 페이지_조회하면_좋아요수_얻는다() {
        int likeCnt = likeRepository.getLikeCountByMno(22L);
        System.out.println(likeCnt);
    }




}
