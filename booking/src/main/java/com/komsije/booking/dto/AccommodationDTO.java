package com.komsije.booking.dto;

import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.AccommodationType;
import com.komsije.booking.model.Address;
import com.komsije.booking.model.TimeSlot;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AccommodationDTO {
    private Long id;
    private String name;
    private String description;
    private Address address;
    private AccommodationType accommodationType;
    private Set<String> amenities = new HashSet<>();
    private int maxGuests;
    private int minGuests;
    private Set<String> photos;
    private boolean isPricePerGuest;
    private int cancellationDeadline;
    private double averageGrade;
    private Set<TimeSlot> availability = new HashSet<>();

    public AccommodationDTO(Long id, String name, String description, Address address, AccommodationType accommodationType, Set<String> amenities, int maxGuests, int minGuests, Set<String> photos, boolean isPricePerGuest, int cancellationDeadline, double averageGrade, Set<TimeSlot> availability) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.accommodationType = accommodationType;
        this.amenities = amenities;
        this.maxGuests = maxGuests;
        this.minGuests = minGuests;
        this.photos = photos;
        this.isPricePerGuest = isPricePerGuest;
        this.cancellationDeadline = cancellationDeadline;
        this.averageGrade = averageGrade;
        this.availability = availability;
    }

    public AccommodationDTO(Accommodation accommodation) {
        this.id = accommodation.getId();
        this.name = accommodation.getName();
        this.description = accommodation.getDescription();
        this.address = accommodation.getAddress();
        this.accommodationType = accommodation.getAccommodationType();
        this.amenities = accommodation.getAmenities();
        this.maxGuests = accommodation.getMaxGuests();
        this.minGuests = accommodation.getMinGuests();
        this.photos = accommodation.getPhotos();
        this.isPricePerGuest = accommodation.isPricePerGuest();
        this.cancellationDeadline = accommodation.getCancellationDeadline();
        this.averageGrade = accommodation.getAverageGrade();
        this.availability = accommodation.getAvailability();
    }

}
