package com.setge.talkingtoday.repository;

import com.setge.talkingtoday.entity.Movie;
import com.setge.talkingtoday.entity.MovieImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class MovieRepositoryTests {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieImageRepository movieImageRepository;

    @Commit
    @Transactional
    @Test
    public void 영화_추가한다() {
        IntStream.rangeClosed(1, 10).forEach(i ->{
            Movie movie = Movie.builder().title("영화"+i).build();
            movieRepository.save(movie);

            int count = (int)(Math.random() * 5) +1;

            for (int j = 0; j < count; j++) {
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("image"+i+".jpg").build();

                movieImageRepository.save(movieImage);
            }
        });
    }

    @Test
    public void 리뷰페이지_조회() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno"));

        Page<Object[]> result = movieRepository.getListPage(pageRequest);

        for (Object[] objects : result.getContent()) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void 영화_조회한다() {
        List<Object[]> result = movieRepository.getMovieWithAll(11L);
        System.out.println(result);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }

//    @Test
//    public void 영화_검색한다() {
//
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
//
//        QMovie qMovie = QMovie.movie;
//
//        String keyword = "마녀";
//
//        BooleanBuilder builder = new BooleanBuilder();
//        BooleanExpression expression = qMovie.title.contains(keyword);
//        builder.and(expression);
//
//        Page<Movie> result = movieRepository.findAll(builder, pageable);
//
//        result.stream().forEach(movie -> {
//            System.out.println(movie);
//        });
//
//    }
}
