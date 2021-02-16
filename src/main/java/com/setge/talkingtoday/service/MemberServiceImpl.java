package com.setge.talkingtoday.service;

import com.setge.talkingtoday.dto.MemberDTO;
import com.setge.talkingtoday.entity.Member;
import com.setge.talkingtoday.entity.Role;
import com.setge.talkingtoday.repository.MemberRepository;
import com.setge.talkingtoday.security.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepo;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Long register(MemberDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword())); // 비밀번호 암호화
        Member member = dtoToEntity(dto);
        member.addMemberRole(Role.USER); // 권한은 유저

        memberRepo.save(member);
        return member.getMid();
    }

}
