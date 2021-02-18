package com.setge.talkingtoday.service;

import com.setge.talkingtoday.dto.MemberDTO;
import com.setge.talkingtoday.entity.Member;

public interface MemberService {

    Long register(MemberDTO dto);

    int duplicateEmailCheck(String email);

    int duplicateNicknameCheck(String nickname);

    void modify(String password, Long mid);


    /**
     *
     * @param dto
     * @return member(Entity)
     */
    default Member dtoToEntity(MemberDTO dto) {
        Member member = Member.builder()
                .mid(dto.getMid())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .fromSocial(false)
                .build();

        return member;

    }

}
