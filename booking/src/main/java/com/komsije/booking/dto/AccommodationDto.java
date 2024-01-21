package com.komsije.booking.dto;

import com.komsije.booking.model.*;
import com.komsije.booking.validators.AccommodationTypeConstraint;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Data
public class AccommodationDto {
    private Long id;
    private String name;
    private String description;
    private AddressDto address;
    @AccommodationTypeConstraint
    private AccommodationType accommodationType;
    private Set<String> amenities = new HashSet<>();
    private int maxGuests;
    private int minGuests;
    private Set<String> photos;
    private boolean isPricePerGuest;
    private int cancellationDeadline;
    private double averageGrade;
    private Set<TimeSlotDto> availability = new HashSet<>();
    private AccommodationStatus status;
    private boolean isAutoApproval;
    private HostDto host;


}
