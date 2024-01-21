package com.komsije.booking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.komsije.booking.model.Review;
import com.komsije.booking.model.ReviewStatus;
import com.komsije.booking.validators.IdentityConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReviewDto {
    @IdentityConstraint
    private Long id;
    @Min(value = 1, message = "Grade must be greater or equal to 1")
    @Max(value = 5, message = "Grade must be less than or equal to 5")
    private int grade;
    @NotEmpty
    private String comment;
    @NotNull
    private UserDto author;
    @NotNull
    private ReviewStatus status;
    @IdentityConstraint
    private Long hostId;
    @IdentityConstraint
    private Long accommodationId;
    @NotNull
    private LocalDateTime date;

}
