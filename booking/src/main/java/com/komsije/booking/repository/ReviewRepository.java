package com.komsije.booking.repository;

import com.komsije.booking.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> getReviewsByIsApprovedIsTrue();
}
