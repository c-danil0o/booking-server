package com.komsije.booking.service;

import com.komsije.booking.model.Review;
import com.komsije.booking.repository.ReviewRepository;
import com.komsije.booking.service.interfaces.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id).orElseGet(null);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Review save(Review accommodation) {
        return reviewRepository.save(accommodation);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> getApprovedReviews() {return reviewRepository.getReviewsByIsApprovedIsTrue();}
}
