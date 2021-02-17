package com.setge.talkingtoday.controller;

import com.setge.talkingtoday.dto.MemberDTO;
import com.setge.talkingtoday.security.AuthMemberDTO;
import com.setge.talkingtoday.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/admin")
    public void admin() {
        log.info("admin..");
    }

    @GetMapping("/signup")
    public void signupForm(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
    }

    @PostMapping("/signup")
    public String signupPost(@Valid MemberDTO dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("register is not valid");
            return "/member/signup";
        }
        return "redirect:/";
    }

    @GetMapping("/member-info")
    public void member(@AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        log.info("로그인 회원 : " + authMemberDTO);
    }


}
