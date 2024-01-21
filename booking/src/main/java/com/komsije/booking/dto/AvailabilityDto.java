package com.komsije.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AvailabilityDto {
    @NotNull
    private Integer cancellationDeadline;
    @NotNull
    private Set<TimeSlotDto> availability = new HashSet<>();
}
