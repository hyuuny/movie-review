package com.setge.talkingtoday.repository;

import com.setge.talkingtoday.entity.Member;
import com.setge.talkingtoday.entity.Movie;
import com.setge.talkingtoday.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 리뷰_등록한다() {
        IntStream.rangeClosed(1, 20).forEach(i ->{
            Long mno = (long)(Math.random() * 3) + 1;
            Long mid = (long)(Math.random() * 13) + 1;
            Movie movie = Movie.builder().mno(mno).build();
            Member member = Member.builder().mid(mid).build();

            Review review = Review.builder()
                    .member(member)
                    .movie(movie)
                    .grade((int)(Math.random()*5)+1)
                    .text("제 생각은 " + i)
                    .build();

            reviewRepository.save(review);
        });
    }

    @Test
    public void 영화_조회한다() {
        Movie movie = Movie.builder().mno(11L).build();

        List<Review> result = reviewRepository.findByMovie(movie);
        result.forEach(movieReview ->{
            System.out.print(movieReview.getReviewnum());
            System.out.print("\t"+movieReview.getGrade());
            System.out.print("\t"+movieReview.getText());
            System.out.print("\t"+movieReview.getMember().getEmail());
            System.out.println("=================================");
        });
    }

    @Commit
    @Transactional
    @Test
    public void 회원_탈퇴한다() {
        Long mid = 2L;
        Member member = Member.builder().mid(mid).build();

        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);
    }
}
