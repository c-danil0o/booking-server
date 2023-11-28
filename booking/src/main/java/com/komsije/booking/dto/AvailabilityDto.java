package com.komsije.booking.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AvailabilityDto {
    private Integer cancellationDeadline;
    private Set<TimeSlotDto> availability = new HashSet<>();
}
