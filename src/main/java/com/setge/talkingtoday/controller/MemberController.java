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
            return "/member/signup";
        }

        memberService.register(dto);
        return "redirect:/";
    }

    @ResponseBody
    @PostMapping("/duplicateEmailCheck")
    public int duplicateEmailCheck(@RequestParam("email") String email) {
        int cnt = memberService.duplicateEmailCheck(email);
        return cnt;
    }

    @ResponseBody
    @PostMapping("/duplicateNicknameCheck")
    public int duplicateNicknameCheck(@RequestParam("nickname") String nickname) {
        int cnt = memberService.duplicateNicknameCheck(nickname);
        return cnt;
    }

    @GetMapping("/member-info")
    public void member(@AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        log.info("로그인 회원 : " + authMemberDTO);
    }

    @PostMapping("/modifyNickname")
    public String modifyNicknamePost(@AuthenticationPrincipal AuthMemberDTO authMemberDTO,
                                     String nickname) {
        memberService.changeNickname(nickname, authMemberDTO.getMid());

        return "redirect:/member/member-info";
    }

    @GetMapping("/password-check")
    public void passwordCheckForm() {}

    @PostMapping("/password-check")
    public String passwordCheckPost(@AuthenticationPrincipal AuthMemberDTO authMemberDTO,
                                    String password) {
        if (!(memberService.isPwdMatchesCheck(password, authMemberDTO.getMid()))) {
            return "/member/password-check";
        }
        return "/member/modify";
    }

    @GetMapping("/modify")
    public void modifyForm() {}

    @PostMapping("/modify")
    public String modifyPassword(String newPwd, @AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        memberService.changePassword(newPwd, authMemberDTO.getMid());
        return "redirect:/";
    }
}
