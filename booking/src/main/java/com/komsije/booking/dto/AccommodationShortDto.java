package com.komsije.booking.dto;

import com.komsije.booking.model.AccommodationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class AccommodationShortDto {
    private Long id;
    private String name;
    private String location;
    private String description;
    private Double averageGrade;
    private Set<String> images;
}
