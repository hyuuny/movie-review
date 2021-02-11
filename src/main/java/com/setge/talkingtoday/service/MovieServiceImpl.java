package com.setge.talkingtoday.service;

import com.setge.talkingtoday.dto.MovieDTO;
import com.setge.talkingtoday.dto.PageRequestDTO;
import com.setge.talkingtoday.dto.PageResultDTO;
import com.setge.talkingtoday.entity.Movie;
import com.setge.talkingtoday.entity.MovieImage;
import com.setge.talkingtoday.repository.MovieImageRepository;
import com.setge.talkingtoday.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Log4j2
@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepo;
    private final MovieImageRepository movieImageRepo;

    @Override
    public Long register(MovieDTO movieDTO) {

        Map<String, Object> entityMap = dtoToEntity(movieDTO); // dto객체를 entity로 변환하여 map에 저장
        Movie movie = (Movie) entityMap.get("movie"); // entityMap({"movie","V"} -> movie
        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList"); // entityMap({"imgList","V"} -> imageList

        movieRepo.save(movie);
        movieImageList.forEach(movieImageRepo::save);

        return movie.getMno();
    }

    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("mno").descending()); // 페이징
        Page<Object[]> result = movieRepo.getListPage(pageable); // (Movie, MovieImage, avg, reviewCnt)

        // MovieEntity -> MovieDTO
        Function<Object[], MovieDTO> fn = (arr -> entitiesToDto(
                (Movie)arr[0],
                (List<MovieImage>)(Arrays.asList((MovieImage)arr[1])),
                (Double) arr[2],
                (Long)arr[3])
        );

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public MovieDTO getMovie(Long mno) {
        List<Object[]> result = movieRepo.getMovieWithAll(mno);
        Movie movie = (Movie) result.get(0)[0]; // Movie Entity는 0번째 이므로..
        List<MovieImage> movieImageList = new ArrayList<>(); // 저장되어 있는 이미지를 담기 위한 리스트 선언

        result.forEach(arr -> {
            MovieImage movieImage = (MovieImage) arr[1];
            movieImageList.add(movieImage);
        });

        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];

        return entitiesToDto(movie, movieImageList, avg, reviewCnt);
    }
}
