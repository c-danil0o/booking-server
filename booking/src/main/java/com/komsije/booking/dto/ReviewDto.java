package com.komsije.booking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.komsije.booking.model.Review;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReviewDto {
    private Long id;
    private int grade;
    private String comment;
    private UserDto author;
    private boolean isApproved;
    private Long hostId;
    private Long accommodationId;
    private LocalDateTime date;

}
