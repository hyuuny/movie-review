package com.setge.talkingtoday.service;

import com.setge.talkingtoday.dto.ReplyDTO;
import com.setge.talkingtoday.entity.Board;
import com.setge.talkingtoday.entity.Reply;
import com.setge.talkingtoday.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepo;

    @Override
    public Long register(ReplyDTO replyDTO) {
        Reply reply = dtoToEntity(replyDTO);
        replyRepo.save(reply);
        return reply.getRno();
    }

    @Override
    public List<ReplyDTO> getList(Long bno) {
        List<Reply> result = replyRepo.getRepliesByBoardOrderByRno(Board.builder().bno(bno).build());
        return result.stream().map(reply -> entityToDto(reply)).collect(Collectors.toList());
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        replyRepo.save(dtoToEntity(replyDTO));

    }

    @Override
    public void remove(Long rno) {
        replyRepo.deleteById(rno);
    }
}
