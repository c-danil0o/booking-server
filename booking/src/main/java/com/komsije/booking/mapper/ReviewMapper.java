package com.komsije.booking.mapper;

import com.komsije.booking.dto.ReviewDto;
import com.komsije.booking.model.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserDtoMapper.class})
public interface ReviewMapper {
    ReviewDto toDto(Review review);
    Review fromDto(ReviewDto reviewDto);
}
