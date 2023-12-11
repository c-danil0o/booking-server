package com.komsije.booking.service;

import com.komsije.booking.dto.ReviewDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
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

    public ReviewDto findById(Long id) throws ElementNotFoundException {
        return mapper.toDto(reviewRepository.findById(id).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!")));
    }

    public List<ReviewDto> findAll() {
        return mapper.toDto(reviewRepository.findAll());
    }

    public ReviewDto save(ReviewDto reviewDto) {
        reviewRepository.save(mapper.fromDto(reviewDto));
        return reviewDto;
    }

    @Override
    public ReviewDto update(ReviewDto reviewDto) throws ElementNotFoundException {
        Review review = reviewRepository.findById(reviewDto.getId()).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
        mapper.update(review, reviewDto);
        reviewRepository.save(review);
        return reviewDto;
    }

    public void delete(Long id) throws ElementNotFoundException {
        if (reviewRepository.existsById(id)){
            reviewRepository.deleteById(id);
        }else{
            throw new ElementNotFoundException("Element with given ID doesn't exist!");
        }

    }

    public List<ReviewDto> getApprovedReviews() {
        return mapper.toDto(reviewRepository.getReviewsByIsApprovedIsTrue());
    }

    public void setApproved(Long id) throws ElementNotFoundException {
        Review review = reviewRepository.findById(id).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
        review.setApproved(true);
        reviewRepository.save(review);
    }

    @Override
    public List<ReviewDto> findByAccommodationId(Long id) throws ElementNotFoundException {
        return mapper.toDto(reviewRepository.findByAccommodationId(id));
    }

    @Override
    public List<ReviewDto> findByHostId(Long id) throws ElementNotFoundException {
        return mapper.toDto(reviewRepository.findByHostId(id));
    }
}
