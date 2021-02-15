package com.setge.talkingtoday.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class Passwordtests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void 엔코더_테스트() {
        String password = "1111";
        String enPw = passwordEncoder.encode(password);

        System.out.println("enPw : " + enPw);

        boolean matchResult = passwordEncoder.matches(password, enPw);
        System.out.println("matchResult : " + matchResult);

    }
}
