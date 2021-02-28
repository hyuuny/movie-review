package com.setge.talkingtoday.service;

import com.setge.talkingtoday.dto.MemberDTO;
import com.setge.talkingtoday.entity.Member;

public interface MemberService {

    Long join(MemberDTO dto);

    int duplicateEmailCheck(String email);

    int duplicateNicknameCheck(String nickname);

    void changeNickname(String nickname, Long mid);

    void changePassword(String newPwd, Long mid);

    boolean isPwdMatchesCheck(String password, Long mid);

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
