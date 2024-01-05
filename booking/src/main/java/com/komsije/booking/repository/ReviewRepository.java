package com.komsije.booking.repository;

import com.komsije.booking.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> getReviewsByIsApprovedIsTrue();
    List<Review> getReviewsByIsApprovedIsFalse();
    @Query("select r from Review r where r.accommodation.id=:id and r.isApproved")
    List<Review> findByAccommodationId(Long id);
    @Query("select r from Review r where r.host.id=:id and r.isApproved")
    List<Review> findByHostId(Long id);
    List<Review> findByAuthorId(Long id);
}
