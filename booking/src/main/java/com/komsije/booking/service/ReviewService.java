package com.komsije.booking.service;

import com.komsije.booking.model.Reservation;
import com.komsije.booking.model.Review;
import com.komsije.booking.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review findOne(Long id) {
        return reviewRepository.findById(id).orElseGet(null);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Review save(Review accommodation) {
        return reviewRepository.save(accommodation);
    }

    public void remove(Long id) {
        reviewRepository.deleteById(id);
    }
}
