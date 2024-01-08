package com.komsije.booking.service;

import com.komsije.booking.dto.ReviewDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.exceptions.ReviewAlreadyExistsException;
import com.komsije.booking.exceptions.ReviewAlreadyReportedException;
import com.komsije.booking.exceptions.ReviewNotFoundException;
import com.komsije.booking.mapper.ReviewMapper;
import com.komsije.booking.model.Review;
import com.komsije.booking.model.ReviewStatus;
import com.komsije.booking.repository.ReviewRepository;
import com.komsije.booking.service.interfaces.AccommodationService;
import com.komsije.booking.service.interfaces.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewMapper mapper;
    @Autowired
    private AccommodationService accommodationService;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public ReviewDto findById(Long id) throws ElementNotFoundException {
        return mapper.toDto(reviewRepository.findById(id).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!")));
    }

    public List<ReviewDto> findAll() {
        return mapper.toDto(reviewRepository.findAll());
    }

    public ReviewDto save(ReviewDto reviewDto) {
        reviewRepository.save(mapper.fromDto(reviewDto));
        return reviewDto;
    }
    @Override
    public ReviewDto saveNewReview(ReviewDto reviewDto) {
        List<Review> reviews = reviewRepository.findByAuthorId(reviewDto.getAuthor().getAccountId());
        if (reviewDto.getAccommodationId() != null){
            for (Review review: reviews){
                if (review.getAccommodation()!= null && review.getAccommodation().getId().equals(reviewDto.getAccommodationId())){
                    throw new ReviewAlreadyExistsException("User has already reviewed this accommodation!");
                }
            }
        }
        if (reviewDto.getHostId() != null){
            for (Review review: reviews){
                if (review.getHost()!= null && review.getHost().getId().equals(reviewDto.getHostId())){
                    throw new ReviewAlreadyExistsException("User has already reviewed this host!");
                }
            }
        }
        reviewRepository.save(mapper.fromDto(reviewDto));
        return reviewDto;
    }


    @Override
    public ReviewDto update(ReviewDto reviewDto) throws ElementNotFoundException {
        Review review = reviewRepository.findById(reviewDto.getId()).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
        mapper.update(review, reviewDto);
        reviewRepository.save(review);
        return reviewDto;
    }

    public void delete(Long id) throws ElementNotFoundException {
        Review review = reviewRepository.findById(id).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
        if (review.getAccommodation() != null){
            reviewRepository.deleteById(id);
            this.accommodationService.updateAverageGrade(review.getAccommodation().getId());
        }else{
            reviewRepository.deleteById(id);
        }

    }
    @Override
    public void deleteHostReview(Long hostId, Long authorId){
        List<Review> reviews = reviewRepository.findByAuthorId(authorId);
        for (Review review: reviews){
            if (review.getHost() != null && review.getHost().getId().equals(hostId)){
                reviewRepository.delete(review);
                return;
            }
        }
        throw new ReviewNotFoundException("Review doesn't exist!");
    }

    @Override
    public void deleteAccommodationReview(Long accommodationId, Long authorId){
        List<Review> reviews = reviewRepository.findByAuthorId(authorId);
        for (Review review: reviews){
            if (review.getAccommodation() != null && review.getAccommodation().getId().equals(accommodationId)){
                this.accommodationService.updateAverageGrade(review.getAccommodation().getId());
                reviewRepository.delete(review);
                return;
            }
        }
        throw new ReviewNotFoundException("Review doesn't exist!");
    }

    public List<ReviewDto> getApprovedReviews() {
        return mapper.toDto(reviewRepository.getReviewsByStatusIs(ReviewStatus.Approved));
    }
    @Override
    public List<ReviewDto> getUnapprovedReviews() {
        return mapper.toDto(reviewRepository.getReviewsByStatusIs(ReviewStatus.Pending));
    }

    @Override
    public void reportReview(Long id){
        Review review = reviewRepository.findById(id).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
        if (review.getStatus().equals(ReviewStatus.Reported)){
            throw new ReviewAlreadyReportedException("This review is already reported!");
        }
        review.setStatus(ReviewStatus.Reported);
        reviewRepository.save(review);
    }

    public void setApproved(Long id) throws ElementNotFoundException {
        Review review = reviewRepository.findById(id).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
        review.setStatus(ReviewStatus.Approved);
        if (review.getAccommodation()!= null){
            this.accommodationService.updateAverageGrade(review.getAccommodation().getId());
        }
        reviewRepository.save(review);
    }

    @Override
    public List<ReviewDto> findByAccommodationId(Long id) throws ElementNotFoundException {
        return mapper.toDto(reviewRepository.findByAccommodationId(id));
    }

    @Override
    public List<ReviewDto> findByHostId(Long id) throws ElementNotFoundException {
        return mapper.toDto(reviewRepository.findByHostId(id));
    }

    @Override
    public ReviewDto findHostReview(Long hostId, Long authorId) {
        List<Review> reviews = reviewRepository.findByHostId(hostId);
        for (Review review: reviews){
            if (review.getAuthor().getId().equals(authorId)){
                return mapper.toDto(review);
            }
        }
        throw new ReviewNotFoundException("Review not found!");
    }
    @Override
    public ReviewDto findAccommodationReview(Long accommodationId, Long authorId){
        List<Review> reviews = reviewRepository.findAllByAccommodationId(accommodationId);
        for (Review review: reviews){
            if (review.getAuthor().getId().equals(authorId)){
                return mapper.toDto(review);
            }
        }
        throw new ReviewNotFoundException("Review not found!");
    }
}
