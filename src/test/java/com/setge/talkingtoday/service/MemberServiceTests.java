package com.setge.talkingtoday.service;

import com.setge.talkingtoday.dto.MemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

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
        service.join(dto);
    }

    @Test
    public void 중복_회원_예외() {
        // given
        MemberDTO dto1 = MemberDTO.builder()
                .email("user1@aaa.com")
                .password(passwordEncoder.encode("1234"))
                .nickname("유저")
                .build();

        // when
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> service.join(dto1));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 이메일입니다.");

//        try {
//            service.join(dto1);
//            fail();
//        } catch (IllegalStateException e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 이메일입니다.");
//        }

        // then


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


}
