package com.komsije.booking.service;

import com.komsije.booking.dto.ReviewDto;
import com.komsije.booking.mapper.ReviewMapper;
import com.komsije.booking.model.Review;
import com.komsije.booking.repository.ReviewRepository;
import com.komsije.booking.service.interfaces.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewMapper mapper;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public ReviewDto findById(Long id) {
        return mapper.toDto(reviewRepository.findById(id).orElseGet(null));
    }

    public List<ReviewDto> findAll() {
        return mapper.toDto(reviewRepository.findAll());
    }
    public ReviewDto save(ReviewDto reviewDto) {
        reviewRepository.save(mapper.fromDto(reviewDto));
        return reviewDto;
    }

    @Override
    public ReviewDto update(ReviewDto reviewDto) {
        Review review = reviewRepository.findById(reviewDto.getId()).orElseGet(null);
        if (review == null){
            return null;
        }
        review = mapper.fromDto(reviewDto);
        reviewRepository.save(review);
        return reviewDto;
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<ReviewDto> getApprovedReviews() {return mapper.toDto(reviewRepository.getReviewsByIsApprovedIsTrue());}
}
