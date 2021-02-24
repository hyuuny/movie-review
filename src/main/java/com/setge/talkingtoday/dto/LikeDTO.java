package com.setge.talkingtoday.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeDTO {

    private Long lid;
    private Long mid;
    private Long mno;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

}
