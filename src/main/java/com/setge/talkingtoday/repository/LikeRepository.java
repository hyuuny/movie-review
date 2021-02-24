package com.setge.talkingtoday.repository;

import com.setge.talkingtoday.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("select count(l) from Like l where l.movie.mno = :mno")
    int getLikeCountByMno(Long mno);

    @Query("select l from Like l where l.member.mid =:mid and l.movie.mno =:mno")
    Like getLibByMidAndMno(@Param("mid") Long mod, @Param("mno") Long mno);

    @Modifying
    @Query("delete from Like l where l.member.mid =:mid and l.movie.mno =:mno")
    void deleteByMidAndMno(@Param("mid") Long mid, @Param("mno") Long mno);
}
