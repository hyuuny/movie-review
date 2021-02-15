package com.setge.talkingtoday.repository;

import com.setge.talkingtoday.entity.Member;
import com.setge.talkingtoday.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @Test
//    public void 회원_추가한다() {
//        IntStream.rangeClosed(1, 10).forEach(i ->{
//            Member member = Member.builder()
//                    .email("user"+i+"@aaa.com")
//                    .password("1234")
//                    .nickname("user"+i)
//                    .build();
//            memberRepo.save(member);
//        });
//    }

    @Test
    public void 시큐리티_유저_추가한다() {

        // 1 - 80 USER
        // 81 - 90 USER, MANAGER
        // 91 - 100 USER, MANAGER, ADMIN
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@aaa.com")
                    .nickname("유저" + i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();

            // default role
            member.addMemberRole(Role.USER);

            if(i > 80){
                member.addMemberRole(Role.MANAGER);
            }
            if (i > 90) {
                member.addMemberRole(Role.ADMIN);
            }

            memberRepo.save(member);
        });
    }

    @Test
    public void 회원_조회한다() {
        Optional<Member> result = memberRepo.findByEmail("user93@aaa.com", false);
        Member member = result.get();
        System.out.println(member);
    }

}
