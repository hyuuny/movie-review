package com.setge.talkingtoday.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.setge.talkingtoday.entity.Board;
import com.setge.talkingtoday.entity.QBoard;
import com.setge.talkingtoday.entity.QMember;
import com.setge.talkingtoday.entity.QReply;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board search1() {

        log.info("search........");

        QBoard qBoard = QBoard.board;
        QReply qReply = QReply.reply;
        QMember qMember = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(qBoard);
        jpqlQuery.leftJoin(qMember).on(qBoard.member.eq(qMember));
        jpqlQuery.leftJoin(qReply).on(qReply.board.eq(qBoard));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(qBoard, qMember.username, qReply.count());
        tuple.groupBy(qBoard);

        log.info("========================");
        log.info(tuple);
        log.info("========================");
        List<Tuple> result = tuple.fetch();
        log.info(result);

        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        log.info("searchPage.....");

        QBoard qBoard = QBoard.board;
        QReply qReply = QReply.reply;
        QMember qMember = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(qBoard);
        jpqlQuery.leftJoin(qMember).on(qBoard.member.eq(qMember));
        jpqlQuery.leftJoin(qReply).on(qReply.board.eq(qBoard));

        // SELECT b, m, count(r) FROM Board b
        // LEFT JOIN b.member m LEFT JOIN Reply r ON r.board = b
        JPQLQuery<Tuple> tuple = jpqlQuery.select(qBoard, qMember, qReply.count());
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = qBoard.bno.gt(0L);

        if (type != null) {
            String[] typeArr = type.split("");

            // 검색 조건
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            for (String t : typeArr) {
                switch (t) {
                    case "t":
                        conditionBuilder.or(qBoard.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(qMember.username.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(qBoard.content.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }

        tuple.where(booleanBuilder);
        tuple.groupBy(qBoard);
        List<Tuple> result = tuple.fetch();
        log.info(result);

        return null;
    }
}
