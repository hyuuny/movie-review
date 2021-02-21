package com.setge.talkingtoday.repository;

import com.setge.talkingtoday.entity.Member;
import com.setge.talkingtoday.entity.Movie;
import com.setge.talkingtoday.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    @Modifying
    @Query("delete from Review mr where mr.member = :member")
    void deleteByMember(Member member);

    @Modifying
    @Query("delete from Review mr where mr.movie.mno = :mno")
    void deleteByMno(Long mno);

}
