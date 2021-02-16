package com.setge.talkingtoday.service;

import com.setge.talkingtoday.entity.Member;
import com.setge.talkingtoday.entity.Role;
import com.setge.talkingtoday.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class MemberServiceTests {

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void 회원가입_테스트한다() {
        Member member = Member.builder()
                .email("test@aaa.com")
                .password(passwordEncoder.encode("1111"))
                .nickname("테스트")
                .build();

        member.addMemberRole(Role.USER);
        memberRepo.save(member);
    }
}
