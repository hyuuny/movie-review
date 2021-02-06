package com.setge.talkingtoday.service;

import com.setge.talkingtoday.dto.BoardDTO;
import com.setge.talkingtoday.dto.PageRequestDTO;
import com.setge.talkingtoday.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService service;

    @Test
    public void 게시글_등록한다() {
        BoardDTO dto = BoardDTO.builder()
                .title("빌런")
                .content("아임빌런")
                .memberEmail("user1@aaa.com")
                .build();
        Long bno = service.register(dto);
    }

    @Test
    public void 게시글_목록가져온다() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageResultDTO<BoardDTO, Object[]> result = service.getList(pageRequestDTO);

        for (BoardDTO dto : result.getDtoList()) {
            System.out.println(dto);
        }
    }

    @Test
    public void 게시글_조회한다() {
        BoardDTO boardDTO = service.get(62L);
        System.out.println(boardDTO);
    }

    @Test
    public void 게시글_삭제한다() {
        service.removeWithReplies(6L);
    }

    @Test
    public void 게시글_수정한다() {
        BoardDTO dto = BoardDTO.builder()
                .bno(62L)
                .title("수정한다구!")
                .content("수정됐어요!")
                .build();
        service.modify(dto);
    }
}
