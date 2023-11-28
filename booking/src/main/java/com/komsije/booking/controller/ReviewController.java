package com.komsije.booking.controller;

import com.komsije.booking.dto.AccommodationDto;
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
        List<ReviewDto> reviewDtos = reviewService.findAll();
        return new ResponseEntity<>(reviewDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable Long id) {

        ReviewDto reviewDto = reviewService.findById(id);

        if (reviewDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }
    @GetMapping(value = "/approved")
    public ResponseEntity<List<ReviewDto>> getApprovedReviews(){
        try{
            List<ReviewDto> reviewDtos = reviewService.getApprovedReviews();

            return new ResponseEntity<>(reviewDtos, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ReviewDto> saveReview(@RequestBody ReviewDto reviewDTO) {
        ReviewDto reviewDto = reviewService.save(reviewDTO);
        return new ResponseEntity<>(reviewDto, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}/approve")
    public ResponseEntity<ReviewDto> approveReview(@PathVariable("id") Long id) {
        ReviewDto reviewDto = reviewService.findById(id);
        if (reviewDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        reviewDto.setApproved(true);
        reviewService.setApproved(reviewDto.getId());
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        ReviewDto reviewDto = reviewService.findById(id);
        if (reviewDto != null) {
            reviewService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
