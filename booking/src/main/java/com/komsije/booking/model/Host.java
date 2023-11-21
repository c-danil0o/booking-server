package com.komsije.booking.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Host extends Account {
    private String firstName;
    private String lastName;
    private Account account;
    private String phone;
    private HashSet<Accommodation> properties;
    private HashSet<Review> hostReviews;
}
