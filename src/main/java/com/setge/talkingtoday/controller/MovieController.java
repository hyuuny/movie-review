package com.setge.talkingtoday.controller;

import com.setge.talkingtoday.dto.MovieDTO;
import com.setge.talkingtoday.dto.MovieImageDTO;
import com.setge.talkingtoday.dto.PageRequestDTO;
import com.setge.talkingtoday.entity.MovieImage;
import com.setge.talkingtoday.security.AuthMemberDTO;
import com.setge.talkingtoday.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/movie")
@Controller
public class MovieController {

    private final MovieService movieService;

    @Value("${com.setge.upload.path}")
    private String uploadPath;

    /**
     * 게시판 리스트 조회
     */
    @GetMapping("/list")
    public void listPage(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", movieService.getList(pageRequestDTO));
    }

    /**
     * 영화 게시글 등록 페이지 조회
     */
    @GetMapping("/register")
    public void movieRegisterForm(@AuthenticationPrincipal AuthMemberDTO authMemberDTO,
                                  Model model) {
        model.addAttribute("member", authMemberDTO);
    }

    /**
     * 영화 게시글 등록
     */
    @PostMapping("/register")
    public String registerPost(MovieDTO movieDTO, RedirectAttributes redirectAttributes) {
        log.info("movieDTO : " + movieDTO);
        Long mno = movieService.register(movieDTO);
        redirectAttributes.addFlashAttribute("msg", mno);
        return "redirect:/movie/list";
    }

    /**
     * 조회, 수정페이지 조회
     */
    @GetMapping({"/read", "/modify"})
    public void read(long mno, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
                     @AuthenticationPrincipal AuthMemberDTO authMemberDTO, Model model) {
        MovieDTO movieDTO = movieService.getMovie(mno);
        model.addAttribute("dto", movieDTO);
        model.addAttribute("memberDTO", authMemberDTO);
    }

    /**
     * 영화 게시글 수정
     * 기존 업로드 파일을 모두 지우고 새로 업로드 된 파일로 변경한다.
     */
    @PostMapping("/modify")
    public String modifyMovie(MovieDTO movieDTO, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                              RedirectAttributes redirectAttributes) {
        log.info(movieDTO + " 게시글 수정");

        List<MovieImageDTO> movieImageDTO = getMovieImageDTOS(movieDTO.getMno());

        movieService.modify(movieDTO);
        deleteFiles(movieImageDTO);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("mno", movieDTO.getMno());
        return "redirect:/movie/read";
    }


    /**
     * 영화 게시글 삭제
     */
    @PostMapping("/remove")
    public String removeMovie(Long mno) {

        List<MovieImageDTO> movieImageDTO = getMovieImageDTOS(mno);

        movieService.removeWithImageAndReviews(mno);
        deleteFiles(movieImageDTO);

        return "redirect:/movie/list";
    }

    /**
     * MovieImageList -> MovieImageDTO
     */
    private List<MovieImageDTO> getMovieImageDTOS(Long mno) {
        List<MovieImage> movieImageList = movieService.getMovieImageList(mno);
        return movieService.movieImageListToMovieImageDTOList(movieImageList);
    }

    /**
     * 경로에 저장된 이미지파일 삭제
     */
    private void deleteFiles(List<MovieImageDTO> imageDTOList) {

        imageDTOList.forEach(image -> {
            try {
                Path filePath = Paths.get(uploadPath + File.separator + image.getPath() + File.separator +
                        image.getUuid() + "_" + image.getImgName());

                // 기존 파일 삭제하고
                Files.deleteIfExists(filePath);

                if (Files.probeContentType(filePath).startsWith("image")) {
                    Path thumbnail = Paths.get(uploadPath + File.separator + image.getPath() + "/s_" +
                            image.getUuid() + "_" + image.getImgName());

                    // 썸네일도 삭제
                    Files.delete(thumbnail);
                }

            } catch (Exception e) {
                log.error("delete file error : " + e.getMessage());
            }
        }); // end forEach
    }


}
