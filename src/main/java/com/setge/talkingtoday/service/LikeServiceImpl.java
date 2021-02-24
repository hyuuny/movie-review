package com.setge.talkingtoday.service;

import com.setge.talkingtoday.dto.LikeDTO;
import com.setge.talkingtoday.entity.Like;
import com.setge.talkingtoday.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepo;


    @Override
    public int getLikeCount(Long mno) {
        return likeRepo.getLikeCountByMno(mno);
    }

    @Override
    public Long register(LikeDTO likeDTO) {
        Like like = dtoToEntity(likeDTO);
        likeRepo.save(like);
        return like.getMovie().getMno();
    }

    @Override
    public void remove(Long mid, Long mno) {
        Like like = likeRepo.getLibByMidAndMno(mid, mno);
        likeRepo.delete(like);
    }

}
