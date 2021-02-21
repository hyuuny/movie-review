package com.setge.talkingtoday.service;

import com.setge.talkingtoday.dto.ReviewDTO;
import com.setge.talkingtoday.entity.Movie;
import com.setge.talkingtoday.entity.Review;
import com.setge.talkingtoday.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepo;

    @Override
    public List<ReviewDTO> getListOfMovie(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();
        List<Review> reviewList = reviewRepo.findByMovie(movie);
        // entity -> DTO
        return reviewList.stream().map(movieReview -> entityToDto(movieReview)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO movieReviewDTO) {
        Review movieReview = dtoToEntity(movieReviewDTO);
        reviewRepo.save(movieReview);
        return movieReview.getReviewnum();
    }

    @Transactional
    @Override
    public void modify(ReviewDTO movieReviewDTO) {

        Optional<Review> reviewOp = reviewRepo.findById(movieReviewDTO.getReviewnum());

        if (reviewOp.isPresent()) {
            Review review = dtoToEntity(movieReviewDTO);
            reviewRepo.save(review);
        }
    }

    @Override
    public void remove(Long reviewnum) {
        reviewRepo.deleteById(reviewnum);
    }
}
