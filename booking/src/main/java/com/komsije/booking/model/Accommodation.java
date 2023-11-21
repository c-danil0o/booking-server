package com.komsije.booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Accommodation {
    @Id
    private Long id;

    private String name;
    private String description;
    @ManyToOne
    private Address address;
    private AccommodationType accommodationType;

    @ElementCollection
    private HashSet<String> amenities;

    @OneToMany
    private HashSet<TimeSlot> availability;

    private int maxGuests;
    private int minGuests;
    @ElementCollection
    private HashSet<String> photos;

    private boolean isPricePerGuest;
    private int cancellationDeadline;
    private boolean isAutoApproval;
    private double averageGrade;

}
