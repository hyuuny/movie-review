package com.setge.talkingtoday.controller;

import com.setge.talkingtoday.dto.MemberDTO;
import com.setge.talkingtoday.security.AuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class ChatRoomController {

    @GetMapping("/chatting")
    public void chatRoom(@AuthenticationPrincipal AuthMemberDTO authMemberDTO,
                         Model model) {
        model.addAttribute("username", authMemberDTO.getNickname());
    }

}
