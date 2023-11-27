package com.komsije.booking.service.interfaces;

import com.komsije.booking.model.Review;
import com.komsije.booking.service.interfaces.crud.CrudService;

import java.util.List;

public interface ReviewService extends CrudService<Review, Long> {
    public List<Review> getApprovedReviews();
}
