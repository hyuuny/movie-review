package com.setge.talkingtoday.service;

import com.setge.talkingtoday.dto.MemberDTO;
import com.setge.talkingtoday.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberServiceTests {

    @Autowired
    private MemberService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void 회원가입_테스트한다() {
        MemberDTO dto = MemberDTO.builder()
                .email("test@aaa.com")
                .password(passwordEncoder.encode("1111"))
                .nickname("테스트")
                .build();
        service.register(dto);
    }

    @Test
    public void 중복이메일_체크한다() {
        // given
        String email = "user1@aaa.com";

        // when
        int cnt = service.duplicateEmailCheck(email);

        // then
        assertThat(cnt).isEqualTo(1);
    }

    @Test
    public void 중복닉네임_체크한다() {
        // given
        String nickname = "테스트";
        String nickname2 = "테스트2";

        // when
        int cnt = service.duplicateNicknameCheck(nickname);
        int cnt2 = service.duplicateNicknameCheck(nickname2);

        // then
        assertThat(cnt).isEqualTo(1);
        assertThat(cnt2).isEqualTo(0);
    }

    @Test
    public void 닉네임_변경한다() {
        //given


    }

}
