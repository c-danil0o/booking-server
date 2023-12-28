package com.komsije.booking.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetAvailabilityPrice {
    private Long accommodationId;
    private LocalDate startDate;
    private LocalDate endDate;
}
