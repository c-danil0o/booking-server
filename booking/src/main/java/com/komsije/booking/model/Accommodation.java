package com.komsije.booking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
@Getter
@Setter
@AllArgsConstructor
public class Accommodation {
    private Long id;
    private String name;
    private String description;
    private Address address;
    private AccommodationType accommodationType;
    private HashSet<String> amenities;
    private HashSet<TimeSlot> availability;
    private int maxGuests;
    private int minGuests;
    private HashSet<String> photos;
    private boolean isPricePerGuest;
    private int cancellationDeadline;
    private boolean isAutoApproval;
    private double averageGrade;

}
