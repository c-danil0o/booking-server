package com.komsije.booking.controller;

import com.komsije.booking.dto.AccommodationDto;
import com.komsije.booking.dto.ReviewDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.exceptions.HasActiveReservationsException;
import com.komsije.booking.model.AccommodationType;
import com.komsije.booking.model.Review;
import com.komsije.booking.service.AccountServiceImpl;
import com.komsije.booking.service.ReviewServiceImpl;
import com.komsije.booking.service.interfaces.AccountService;
import com.komsije.booking.service.interfaces.ReviewService;
import com.komsije.booking.validators.IdentityConstraint;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/reviews")
@Validated
public class ReviewController {
    private final ReviewService reviewService;

    private final AccountService accountService;

    @Autowired
    public ReviewController(ReviewService reviewService, AccountService accountService) {
        this.reviewService = reviewService;
        this.accountService = accountService;
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        List<ReviewDto> reviewDtos = reviewService.findAll();
        return new ResponseEntity<>(reviewDtos, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<ReviewDto> getReview(@IdentityConstraint @PathVariable Long id) {
        ReviewDto reviewDto = reviewService.findById(id);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }
    @GetMapping(value = "/approved")
    public ResponseEntity<List<ReviewDto>> getApprovedReviews(){
        List<ReviewDto> reviewDtos = reviewService.getApprovedReviews();
        return new ResponseEntity<>(reviewDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/unapproved")
    public ResponseEntity<List<ReviewDto>> getUnapprovedReviews(){
        List<ReviewDto> reviewDtos = reviewService.getUnapprovedReviews();
        return new ResponseEntity<>(reviewDtos, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ReviewDto> saveReview(@RequestBody ReviewDto reviewDTO) {
        ReviewDto reviewDto = null;
        reviewDto = reviewService.saveNewReview(reviewDTO);
        return new ResponseEntity<>(reviewDto, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('Admin')")
    @PatchMapping(value = "/{id}/approve")
    public ResponseEntity<ReviewDto> approveReview(@PathVariable("id") Long id) {
        ReviewDto reviewDto = null;
        reviewService.setApproved(id);
        reviewDto = reviewService.findById(id);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Host')")
    @PatchMapping(value = "/{id}/report")
    public ResponseEntity<ReviewDto> reportReview(@PathVariable("id") Long id) {
        ReviewDto reviewDto = null;
        reviewService.reportReview(id);
        reviewDto = reviewService.findById(id);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('Admin', 'Guest')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('Admin', 'Guest')")
    @DeleteMapping(value = "/deletehost/{hostId}/{userId}")
    public ResponseEntity<Void> deleteHostReview(@PathVariable Long hostId, @PathVariable Long userId) {
        reviewService.deleteHostReview(hostId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('Admin', 'Guest')")
    @DeleteMapping(value = "/deleteacc/{accommodationId}/{userId}")
    public ResponseEntity<Void> deleteAccommodationReview(@PathVariable Long accommodationId, @PathVariable Long userId) {
        reviewService.deleteAccommodationReview(accommodationId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/acc")
    public ResponseEntity<List<ReviewDto>> getByAccommodationId(@RequestParam Long accommodationId ) {
        List<ReviewDto> reviewDtos = reviewService.findByAccommodationId(accommodationId);
        return new ResponseEntity<>(reviewDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/host")
    public ResponseEntity<List<ReviewDto>> getByHostId(@RequestParam Long hostId ) {
        List<ReviewDto> reviewDtos = reviewService.findByHostId(hostId);
        return new ResponseEntity<>(reviewDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/findhost/{hostId}/{authorId}")
    public ResponseEntity<ReviewDto> findHostReview(@PathVariable Long hostId, @PathVariable Long authorId) {
        ReviewDto reviewDto = reviewService.findHostReview(hostId, authorId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @GetMapping(value = "/findacc/{accId}/{authorId}")
    public ResponseEntity<ReviewDto> findAccommodationReview(@PathVariable Long accId, @PathVariable Long authorId) {
        ReviewDto reviewDto = reviewService.findAccommodationReview(accId, authorId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }


}
