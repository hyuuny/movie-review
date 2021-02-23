package com.setge.talkingtoday.controller;

import com.setge.talkingtoday.dto.MovieDTO;
import com.setge.talkingtoday.dto.PageRequestDTO;
import com.setge.talkingtoday.entity.Movie;
import com.setge.talkingtoday.security.AuthMemberDTO;
import com.setge.talkingtoday.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/movie")
@Controller
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/list")
    public void listPage(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", movieService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void movieRegisterForm(@AuthenticationPrincipal AuthMemberDTO authMemberDTO,
                                  Model model) {
        model.addAttribute("member", authMemberDTO);
    }

    @PostMapping("/register")
    public String registerPost(MovieDTO movieDTO, RedirectAttributes redirectAttributes) {
        log.info("movieDTO : " + movieDTO);
        Long mno = movieService.register(movieDTO);
        redirectAttributes.addFlashAttribute("msg", mno);
        return "redirect:/movie/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(long mno, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
                     @AuthenticationPrincipal AuthMemberDTO authMemberDTO, Model model) {
        MovieDTO movieDTO = movieService.getMovie(mno);
        model.addAttribute("dto", movieDTO);
        model.addAttribute("memberDTO", authMemberDTO);
    }

    @PostMapping("/modify")
    public String modifyMovie(MovieDTO movieDTO, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                              RedirectAttributes redirectAttributes) {
        log.info(movieDTO + " 게시글 수정");
        movieService.modify(movieDTO);
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("mno", movieDTO.getMno());
        return "redirect:/movie/read";
    }


    @PostMapping("/remove")
    public String removeMovie(Long mno) {
        movieService.removeWithImageAndReplies(mno);
        return "redirect:/movie/list";
    }
}
