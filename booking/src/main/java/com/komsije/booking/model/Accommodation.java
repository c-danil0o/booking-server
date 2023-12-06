package com.komsije.booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Address address;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccommodationType accommodationType;

    @ElementCollection
    private Set<String> amenities = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TimeSlot> availability = new HashSet<>();
    @Column(nullable = false)
    private int maxGuests;
    @ManyToOne(fetch = FetchType.LAZY)
    private Host host;
    @Column(nullable = false)
    private int minGuests;
    @ElementCollection
    private Set<String> photos = new HashSet<>();
    private boolean isPricePerGuest;
    @Column(nullable = false)
    private int cancellationDeadline;
    @Column(nullable = false)
    private boolean isAutoApproval;
    @Column(nullable = false)
    private double averageGrade;
    @Column(nullable = false)
    private boolean isApproved;
    @Column(nullable = true)
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "favorites")
    private Set<Guest> favoriteTo = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accommodation", cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet<>();



}
