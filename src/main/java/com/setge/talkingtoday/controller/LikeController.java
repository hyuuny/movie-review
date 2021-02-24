package com.setge.talkingtoday.controller;

import com.setge.talkingtoday.dto.LikeDTO;
import com.setge.talkingtoday.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    // 하나로 분기하자
    // 1. 조회
    // 2. 있으면 삭제
    // 3. 없으면 등록

    @GetMapping("/{mno}/likeCnt")
    public ResponseEntity<Integer> getLikeCount(@PathVariable("mno") Long mno) {
        int likeCount = likeService.getLikeCount(mno);
        return new ResponseEntity<>(likeCount, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Long> clickLike(@RequestBody LikeDTO likeDTO) {
        System.out.println("겟 : " + likeDTO);
        Long mno = likeService.register(likeDTO);
        return new ResponseEntity<>(mno, HttpStatus.OK);
    }

    @DeleteMapping("/{mid}/{mno}")
    public ResponseEntity<String> remove(@PathVariable("mid") Long mid, @PathVariable("mno") Long mno) {
        likeService.remove(mid, mno);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
