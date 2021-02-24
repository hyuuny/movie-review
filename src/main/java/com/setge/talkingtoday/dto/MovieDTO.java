package com.setge.talkingtoday.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

    private Long mno;
    private Long mid;
    private String nickname;
    private String title;

    @Builder.Default
    private List<MovieImageDTO> imageDTOList = new ArrayList();

    private double avg;             // 영화 평균평점
    private int reviewCnt;          // 리뷰 수
    private Long likeCnt;           // 좋아요 수
    private LocalDateTime regDate;  // 등록일
    private LocalDateTime modDate;  // 수정일

}
