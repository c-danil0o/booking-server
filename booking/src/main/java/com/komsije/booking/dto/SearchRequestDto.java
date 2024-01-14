package com.komsije.booking.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchRequestDto {
    private String place;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int guests;
}
