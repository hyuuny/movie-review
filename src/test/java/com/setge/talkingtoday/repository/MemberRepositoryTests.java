package com.setge.talkingtoday.repository;

import com.setge.talkingtoday.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepo;

    @Test
    public void 회원_추가한다() {
        IntStream.rangeClosed(1, 10).forEach(i ->{
            Member member = Member.builder()
                    .email("user"+i+"@aaa.com")
                    .password("1234")
                    .nickname("user"+i)
                    .build();
            memberRepo.save(member);
        });
    }
}
