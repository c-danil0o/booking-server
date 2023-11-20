package com.komsije.booking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Review {
    private int grade;
    private String comment;
    private Account author;
    private boolean isApproved;
}
