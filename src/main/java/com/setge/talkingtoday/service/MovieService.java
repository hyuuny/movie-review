package com.setge.talkingtoday.service;

import com.setge.talkingtoday.dto.MovieDTO;
import com.setge.talkingtoday.dto.MovieImageDTO;
import com.setge.talkingtoday.dto.PageRequestDTO;
import com.setge.talkingtoday.dto.PageResultDTO;
import com.setge.talkingtoday.entity.Movie;
import com.setge.talkingtoday.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {

    Long register(MovieDTO movieDTO);

    PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO);

    MovieDTO getMovie(Long mno);

    void removeWithImageAndReplies(Long mno);

    void modify(MovieDTO movieDTO);


    /**
     *
     * @param movie
     * @param movieImages
     * @param avg
     * @param reviewCnt
     * @return MovieDTO(Movie, MovieImage, 평균평점, 리뷰 수)
     */
    // 리스트에 뿌리기 위한 entitiesToDto
    default MovieDTO entitiesToDto(Movie movie, List<MovieImage> movieImages, Double avg, Long reviewCnt) {

        MovieDTO movieDTO = MovieDTO.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .regDate(movie.getRegDate())
                .modDate(movie.getModDate())
                .build();

        List<MovieImageDTO> movieImageDTOList = movieImages.stream().map(movieImage -> {
            return MovieImageDTO.builder()
                    .imgName(movieImage.getImgName())
                    .path(movieImage.getPath())
                    .uuid(movieImage.getUuid())
                    .build();
        }).collect(Collectors.toList());

        movieDTO.setImageDTOList(movieImageDTOList);
        movieDTO.setAvg(avg);
        movieDTO.setReviewCnt(reviewCnt.intValue());

        return movieDTO;
    }

    /**
     *
     * @param movieDTO
     * @return Map(entity) Movie & movieImage
     */
    default Map<String, Object> dtoToEntity(MovieDTO movieDTO) {
        // MovieDto -> Movie
        Map<String, Object> entityMap = new HashMap<>();

        Movie movie = Movie.builder()
                .mno(movieDTO.getMno())
                .title(movieDTO.getTitle())
                .build();

        entityMap.put("movie", movie);

        // MovieImageDTO - > MovieImage
        List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();
        if (imageDTOList != null && imageDTOList.size() > 0) {
            List<MovieImage> movieImageList = imageDTOList.stream().map(movieImageDTO -> {
                System.out.println("movieImageDTO : " + movieImageDTO);
                MovieImage movieImage = MovieImage.builder()
                        .path(movieImageDTO.getPath())
                        .imgName(movieImageDTO.getImgName())
                        .uuid(movieImageDTO.getUuid())
                        .movie(movie)
                        .build();
                return movieImage;
            }).collect(Collectors.toList());
            entityMap.put("imgList", movieImageList);
        }
        return entityMap;
    }

}
