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
@AllArgsConstructor
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

    public AccommodationDTO(Accommodation accommodation){
        this.id=accommodation.getId();
        this.name=accommodation.getName();
        this.description=accommodation.getDescription();
        this.address=accommodation.getAddress();
        this.accommodationType=accommodation.getAccommodationType();
        this.amenities=accommodation.getAmenities();
        this.maxGuests=accommodation.getMaxGuests();
        this.minGuests=accommodation.getMinGuests();
        this.photos=accommodation.getPhotos();
        this.isPricePerGuest=accommodation.isPricePerGuest();
        this.cancellationDeadline=accommodation.getCancellationDeadline();
        this.averageGrade=accommodation.getAverageGrade();
    }

}
