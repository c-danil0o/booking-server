package com.komsije.booking.controller;

import com.komsije.booking.dto.ReservationDTO;
import com.komsije.booking.dto.ReviewDTO;
import com.komsije.booking.model.Reservation;
import com.komsije.booking.model.Review;
import com.komsije.booking.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<ReviewDTO>> getAllReviews(){
        List<Review> reviews = reviewService.findAll();

        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        for (Review review : reviews) {
            reviewDTOs.add(new ReviewDTO(review));
        }
        return new ResponseEntity<>(reviewDTOs, HttpStatus.OK);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable Long id) {

        Review review = reviewService.findOne(id);

        // studen must exist
        if (review == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ReviewDTO(review), HttpStatus.OK);
    }
}
