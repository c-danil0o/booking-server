package com.komsije.booking.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SearchAccommodationsDto {
    private String place;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int guests;
}
