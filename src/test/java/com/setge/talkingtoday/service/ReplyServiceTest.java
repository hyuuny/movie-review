package com.setge.talkingtoday.service;

import com.setge.talkingtoday.dto.ReplyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest

public class ReplyServiceTest {

    @Autowired
    private ReplyService service;

    @Test
    public void 리플_조회한다() {
        List<ReplyDTO> result = service.getList(3L);
        result.stream().forEach(System.out::println);
    }

    @Test
    public void 리플_등록한다() {

    }
}
