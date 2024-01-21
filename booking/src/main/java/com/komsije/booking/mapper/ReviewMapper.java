package com.komsije.booking.mapper;

import com.komsije.booking.dto.*;
import com.komsije.booking.model.Reservation;
import com.komsije.booking.model.Review;
import com.komsije.booking.repository.ReviewRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring", uses = {UserDtoMapper.class, HostMapper.class, AccommodationMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public abstract class ReviewMapper {
    @Autowired
    private UserDtoMapper userDtoMapper;
    @Autowired
    private HostMapper hostMapper;
    @Autowired
    private AccommodationMapper accommodationMapper;
    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewDto toDto(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setDate(review.getDate());
        reviewDto.setAuthor(userDtoMapper.toDto(review.getAuthor()));
        reviewDto.setComment(review.getComment());
        reviewDto.setStatus(review.getStatus());
        if (review.getHost() != null)
            reviewDto.setHostId(review.getHost().getId());
        if (review.getAccommodation() != null)
            reviewDto.setAccommodationId(review.getAccommodation().getId());
        reviewDto.setGrade(review.getGrade());
        return reviewDto;
    }
    public Review fromDto(ReviewDto reviewDto){
        if (reviewRepository.existsById(reviewDto.getId())){
            return reviewRepository.findById(reviewDto.getId()).orElse(null);
        }else {


            Review review = new Review();
            review.setId(reviewDto.getId());
            review.setDate(reviewDto.getDate());
            review.setAuthor(userDtoMapper.fromDtoModel(reviewDto.getAuthor()));
            review.setComment(reviewDto.getComment());
            review.setStatus(reviewDto.getStatus());
            if (reviewDto.getHostId() != null) {
                HostDto hostDto = new HostDto();
                hostDto.setId(reviewDto.getHostId());
                review.setHost(hostMapper.fromDto(hostDto));
            }
            if (reviewDto.getAccommodationId() != null) {
                AccommodationDto accommodationDto = new AccommodationDto();
                accommodationDto.setId(reviewDto.getAccommodationId());
                review.setAccommodation(accommodationMapper.fromDto(accommodationDto));
            }
            review.setGrade(reviewDto.getGrade());
            return review;
        }
    }

    public abstract List<ReviewDto> toDto(List<Review> reviewList);
    public abstract void update(@MappingTarget Review review, ReviewDto reviewDto);
}
