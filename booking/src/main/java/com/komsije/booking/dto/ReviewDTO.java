package com.komsije.booking.dto;

import com.komsije.booking.model.Account;
import com.komsije.booking.model.Review;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;
    private int grade;
    private String comment;
    private String author;

    public ReviewDTO(Review review) {
        this.id= review.getId();
        this.grade=review.getGrade();
        this.comment=review.getComment();
        this.author=review.getAuthor().getEmail();
    }
}
