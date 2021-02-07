package com.setge.talkingtoday.repository;

import com.setge.talkingtoday.entity.Board;
import com.setge.talkingtoday.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {

    @Query("select b, m from Board b left JOIN b.member m where b.bno = :bno")
    Object getBoardWithMember(@Param("bno") Long bno);

    @Query("select b, r FROM Board b left JOIN Reply r on r.board = b where b.bno=:bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    @Query(value ="SELECT b, m, count(r) " +
            " FROM Board b " +
            " LEFT JOIN b.member m " +
            " LEFT JOIN Reply r ON r.board = b " +
            " GROUP BY b",
            countQuery ="SELECT count(b) FROM Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    @Query("SELECT b, m, count(r) " +
            " FROM Board b LEFT JOIN b.member m " +
            " LEFT OUTER JOIN Reply r ON r.board = b" +
            " WHERE b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);


}
