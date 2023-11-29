package com.komsije.booking.service.interfaces;

import com.komsije.booking.dto.ReviewDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.model.Review;
import com.komsije.booking.service.interfaces.crud.CrudService;

import java.util.List;

public interface ReviewService extends CrudService<ReviewDto, Long> {
    public List<ReviewDto> getApprovedReviews();
    public void setApproved(Long id) throws ElementNotFoundException;
}
