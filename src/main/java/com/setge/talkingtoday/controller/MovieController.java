package com.setge.talkingtoday.controller;

import com.setge.talkingtoday.dto.MovieDTO;
import com.setge.talkingtoday.dto.PageRequestDTO;
import com.setge.talkingtoday.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public void movieRegisterForm() {
    }

    @PostMapping("/register")
    public String registerPost(MovieDTO movieDTO, RedirectAttributes redirectAttributes) {
        log.info("movieDTO : " + movieDTO);
        Long mno = movieService.register(movieDTO);
        redirectAttributes.addFlashAttribute("msg", mno);
        return "redirect:/movie/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(long mno, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model) {
        MovieDTO movieDTO = movieService.getMovie(mno);
        model.addAttribute("dto", movieDTO);
    }
}
