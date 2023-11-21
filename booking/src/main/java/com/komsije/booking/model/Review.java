package com.komsije.booking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Review {
    @Id
    private Long id;
    private int grade;
    private String comment;

    @ManyToOne
    private Account author;
    private boolean isApproved;
}
