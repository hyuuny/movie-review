package com.setge.talkingtoday.dto;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {

    private List<DTO> dtoList;

    private int totalPage;  // 총 페이지 번호
    private int page;       // 현재 페이지 번호
    private int size;       // 목록 사이즈
    private int start;      // 시작 페이지 번호
    private int end;        // 끝 페이지 번호
    private boolean prev;   // 이전
    private boolean next;   // 다음
    private List<Integer> pageList; // 페이지 번호 목록

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        int tempEnd = (int)(Math.ceil(page/10.0)) * 10; // page가 13이면 1.3 -> 2.0 *10 -> 20
        start = tempEnd - 9;
        prev = start > 1; // 1보다 크면 이전 페이지로 이동할 수 있게
        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage > tempEnd;
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

}
