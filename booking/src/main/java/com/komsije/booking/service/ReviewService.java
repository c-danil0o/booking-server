package com.komsije.booking.service;

import com.komsije.booking.model.Reservation;
import com.komsije.booking.model.Review;
import com.komsije.booking.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public Review FindOne(Long id) {return reviewRepository.findById(id).orElseGet(null);}
    public List<Review> FindAll() {return reviewRepository.findAll();}
    public Review Save(Review accommodation) {return reviewRepository.save(accommodation);}
    public void Remove(Long id) {reviewRepository.deleteById(id);}
}
