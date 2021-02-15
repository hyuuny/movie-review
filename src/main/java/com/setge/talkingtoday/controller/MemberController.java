package com.setge.talkingtoday.controller;

import com.setge.talkingtoday.security.AuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@RequestMapping("/member")
@Controller
public class MemberController {

    @GetMapping("/all")
    public void all() {
        log.info("all..");
    }

    @GetMapping("/member-info")
    public void member(@AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        log.info("로그인 회원 : " + authMemberDTO);
    }

    @GetMapping("/admin")
    public void admin() {
        log.info("admin..");
    }

}
