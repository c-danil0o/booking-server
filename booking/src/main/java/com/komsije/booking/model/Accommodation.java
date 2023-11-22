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
    @ManyToOne
    private Address address;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccommodationType accommodationType;

    @ElementCollection
    private Set<String> amenities = new HashSet<>();

    @OneToMany
    private Set<TimeSlot> availability = new HashSet<>();
    @Column(nullable = false)
    private int maxGuests;
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

}
