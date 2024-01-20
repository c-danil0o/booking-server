package com.komsije.booking.repository;

import com.komsije.booking.model.Review;
import com.komsije.booking.model.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> getReviewsByStatusIs(ReviewStatus status);
    @Query("select r from Review r where r.accommodation.id=:id and r.status!='Pending'")
    List<Review> findByAccommodationId(Long id);

    @Query("select r from Review r where r.accommodation.id=:id")
    List<Review> findAllByAccommodationId(@Param("id") Long id);
    @Query("select r from Review r where r.host.id=:id")
    List<Review> findByHostId(@Param("id") Long id);
    List<Review> findByAuthorId(Long id);
}
