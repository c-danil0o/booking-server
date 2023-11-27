package com.komsije.booking.dto;

import com.komsije.booking.model.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private int grade;
    private String comment;
    private Long authorId;

    public ReviewDto(Review review) {
        this.id= review.getId();
        this.grade=review.getGrade();
        this.comment=review.getComment();
        this.authorId=review.getAuthor().getId();
    }
}
