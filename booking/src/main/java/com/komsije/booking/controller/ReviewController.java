package com.komsije.booking.controller;

import com.komsije.booking.dto.AccountDTO;
import com.komsije.booking.dto.ReservationDTO;
import com.komsije.booking.dto.ReviewDTO;
import com.komsije.booking.model.Account;
import com.komsije.booking.model.Reservation;
import com.komsije.booking.model.Review;
import com.komsije.booking.service.AccountService;
import com.komsije.booking.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    private final AccountService accountService;

    @Autowired
    public ReviewController(ReviewService reviewService, AccountService accountService) {
        this.reviewService = reviewService;
        this.accountService = accountService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
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
    @GetMapping(value = "/approved")
    public ResponseEntity<List<ReviewDTO>> getApprovedReviews(){
        try{
            List<Review> reviews = reviewService.getApprovedReviews();

            List<ReviewDTO> reviewDTOs = new ArrayList<>();
            for (Review review : reviews) {
                reviewDTOs.add(new ReviewDTO(review));
            }
            return new ResponseEntity<>(reviewDTOs, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ReviewDTO> saveReview(@RequestBody ReviewDTO reviewDTO) {

        Review review = new Review();
        review.setGrade(reviewDTO.getGrade());
        review.setComment(reviewDTO.getComment());
        review.setAuthor(accountService.findOne(reviewDTO.getAuthorId()));

        review = reviewService.save(review);
        return new ResponseEntity<>(new ReviewDTO(review), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {

        Review review = reviewService.findOne(id);

        if (review != null) {
            reviewService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
