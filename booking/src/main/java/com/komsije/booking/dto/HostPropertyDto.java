package com.komsije.booking.dto;

import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.AccommodationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HostPropertyDto {
    private Long id;
    private String name;
    private String location;
    private AccommodationStatus status;

}
