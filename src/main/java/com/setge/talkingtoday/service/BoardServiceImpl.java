package com.setge.talkingtoday.service;

import com.setge.talkingtoday.dto.BoardDTO;
import com.setge.talkingtoday.dto.PageRequestDTO;
import com.setge.talkingtoday.dto.PageResultDTO;
import com.setge.talkingtoday.entity.Board;
import com.setge.talkingtoday.entity.Member;
import com.setge.talkingtoday.repository.BoardRepository;
import com.setge.talkingtoday.repository.MemberRepository;
import com.setge.talkingtoday.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepo;
    private final ReplyRepository replyRepo;

    @Override
    public Long register(BoardDTO dto) {
        Board board = dtoToEntity(dto);
        boardRepo.save(board);
        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        Function<Object[], BoardDTO> fn = (entity ->
            entityToDto((Board) entity[0], (Member) entity[1], (Long) entity[2]));

        Page<Object[]> result = boardRepo.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending())
        );

        return new PageResultDTO<>(result, fn);
    }

    @Transactional
    @Override
    public BoardDTO get(Long bno) {
        boardRepo.viewCntUp(bno);
        Object result = boardRepo.getBoardByBno(bno);
        Object[] arr = (Object[]) result;
        return entityToDto((Board) arr[0], (Member) arr[1], (Long) arr[2]);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        replyRepo.deleteByBno(bno); // 리플 먼저 삭제하고
        boardRepo.deleteById(bno);  // 게시물 삭제
    }

    @Transactional
    @Override
    public void modify(BoardDTO dto) {
        Board board = boardRepo.getOne(dto.getBno());

        board.changeTitle(dto.getTitle());
        board.changeContent(dto.getContent());

        // JPA는 자동감지 기능이 있어서 save를 하지 않아도 되자만, save해주자.
        boardRepo.save(board);
    }



}
