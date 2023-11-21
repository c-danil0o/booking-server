package com.komsije.booking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Host extends Account {
    @Column(nullable = false)

    private String firstName;
    @Column(nullable = false)

    private String lastName;
    @Column(nullable = false)

    private String phone;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private HashSet<Accommodation> properties = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private HashSet<Review> hostReviews = new HashSet<>();
}
