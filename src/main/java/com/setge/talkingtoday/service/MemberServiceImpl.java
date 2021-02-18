package com.setge.talkingtoday.service;

import com.setge.talkingtoday.dto.MemberDTO;
import com.setge.talkingtoday.entity.Member;
import com.setge.talkingtoday.entity.Role;
import com.setge.talkingtoday.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Transactional(readOnly = true) // register 외에는 전부 읽기만..
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepo;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public Long register(MemberDTO dto) {

        dto.setPassword(passwordEncoder.encode(dto.getPassword())); // 비밀번호 암호화
        Member member = dtoToEntity(dto);
        member.addMemberRole(Role.USER); // 권한은 유저

        memberRepo.save(member);
        return member.getMid();
    }

    @Override
    public int duplicateEmailCheck(String email) {
        Optional<Member> result = memberRepo.findByEmail(email);
        return result.isPresent() ? 1 : 0;
    }

    @Override
    public int duplicateNicknameCheck(String nickname) {
        Optional<Member> result = memberRepo.findByNickname(nickname);
        return result.isPresent() ? 1 : 0;
    }

    @Override
    public void modify(String password, Long mid) {
        Member member = memberRepo.findById(mid).get();



    }


}
