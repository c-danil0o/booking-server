package com.komsije.booking.dto;

import com.komsije.booking.model.AccommodationType;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class SearchResponseDto {
    private Long id;
    private String name;
    private String description;
    private AddressDto address;
    private AccommodationType accommodationType;
    private Set<String> amenities = new HashSet<>();
    private int maxGuests;
    private int minGuests;
    private Set<String> photos;
    private int cancellationDeadline;
    private double averageGrade;
    private double price;
    private double pricePerNight;
}
