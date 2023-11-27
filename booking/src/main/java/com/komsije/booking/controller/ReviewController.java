package com.komsije.booking.controller;

import com.komsije.booking.dto.ReviewDto;
import com.komsije.booking.model.Review;
import com.komsije.booking.service.AccountServiceImpl;
import com.komsije.booking.service.ReviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/reviews")
public class ReviewController {
    private final ReviewServiceImpl reviewService;

    private final AccountServiceImpl accountService;

    @Autowired
    public ReviewController(ReviewServiceImpl reviewService, AccountServiceImpl accountService) {
        this.reviewService = reviewService;
        this.accountService = accountService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        List<Review> reviews = reviewService.findAll();

        List<ReviewDto> reviewDtos = new ArrayList<>();
        for (Review review : reviews) {
            reviewDtos.add(new ReviewDto(review));
        }
        return new ResponseEntity<>(reviewDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable Long id) {

        Review review = reviewService.findOne(id);

        // studen must exist
        if (review == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ReviewDto(review), HttpStatus.OK);
    }
    @GetMapping(value = "/approved")
    public ResponseEntity<List<ReviewDto>> getApprovedReviews(){
        try{
            List<Review> reviews = reviewService.getApprovedReviews();

            List<ReviewDto> reviewDtos = new ArrayList<>();
            for (Review review : reviews) {
                reviewDtos.add(new ReviewDto(review));
            }
            return new ResponseEntity<>(reviewDtos, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ReviewDto> saveReview(@RequestBody ReviewDto reviewDTO) {

        Review review = new Review();
        review.setGrade(reviewDTO.getGrade());
        review.setComment(reviewDTO.getComment());
        review.setAuthor(accountService.findOne(reviewDTO.getAuthorId()));

        review = reviewService.save(review);
        return new ResponseEntity<>(new ReviewDto(review), HttpStatus.CREATED);
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
