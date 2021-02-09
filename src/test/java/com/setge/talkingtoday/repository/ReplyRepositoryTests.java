package com.setge.talkingtoday.repository;

import com.setge.talkingtoday.entity.Board;
import com.setge.talkingtoday.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepo;

    @Test
    public void 댓글_등록한다() {
        IntStream.rangeClosed(1 ,10).forEach(i ->{
            long bno = (long)(Math.random() * 30) +1;
            Board board = Board.builder().bno(bno).build();
            Reply reply = Reply.builder()
                    .text("댓글"+i)
                    .board(board)
                    .replyer("헤헤")
                    .build();
            replyRepo.save(reply);
        });
    }

    @Test
    public void 댓글_로딩테스트한다() {
        Optional<Reply> result = replyRepo.findById(1L);

        Reply reply = result.get();
        System.out.println(reply);
        System.out.println(reply.getBoard());
    }

    @Test
    public void 댓글목록_조회한다() {
        List<Reply> replyList = replyRepo.getRepliesByBoardOrderByRno(Board.builder()
                .bno(3L).build());

        replyList.forEach(System.out::println);
    }


}
