package com.setge.talkingtoday.service;

import com.setge.talkingtoday.dto.BoardDTO;
import com.setge.talkingtoday.dto.PageRequestDTO;
import com.setge.talkingtoday.dto.PageResultDTO;
import com.setge.talkingtoday.entity.Board;
import com.setge.talkingtoday.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    BoardDTO get(Long bno);

    void removeWithReplies(Long bno);

    void modify(BoardDTO dto);


    /**
     *
     * @param dto
     * @return board(Entity)
     */
    default Board dtoToEntity(BoardDTO dto) {
        Member member = Member.builder().email(dto.getMemberEmail()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .member(member)
                .build();
        return board;
    }

    /**
     *
     * @param board
     * @param member
     * @param replyCnt
     * @return board(DTO)
     */
    default BoardDTO entityToDto(Board board, Member member, Long replyCnt) {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .memberEmail(member.getEmail())
                .memberName(member.getNickname())
                .replyCnt(replyCnt.intValue()) // int 처리
                .viewCnt(board.getViewCnt())
                .build();
        return boardDTO;
    }

}
