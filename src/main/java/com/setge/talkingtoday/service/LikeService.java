package com.setge.talkingtoday.service;

import com.setge.talkingtoday.dto.LikeDTO;
import com.setge.talkingtoday.entity.Like;
import com.setge.talkingtoday.entity.Member;
import com.setge.talkingtoday.entity.Movie;

public interface LikeService {

    int getLikeCount(Long mno);

    Long register(LikeDTO likeDTO);

    void remove(Long mid, Long mno);


    default Like dtoToEntity(LikeDTO likeDTO) {
        Member member = Member.builder().mid(likeDTO.getMid()).build();
        Movie movie = Movie.builder().mno(likeDTO.getMno()).build();

        return Like.builder()
                .lid(likeDTO.getLid())
                .member(member)
                .movie(movie)
                .build();
    }

    default LikeDTO entityToDto(Like like) {

        return LikeDTO.builder()
                .lid(like.getLid())
                .mid(like.getMember().getMid())
                .mno(like.getMovie().getMno())
                .regDate(like.getRegDate())
                .modDate(like.getModDate())
                .build();
    }
}
