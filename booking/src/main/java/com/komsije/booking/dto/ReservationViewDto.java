package com.komsije.booking.dto;

import com.komsije.booking.model.ReservationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationViewDto {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double price;
    private String accommodationName;
    private String guestEmail;
    private ReservationStatus reservationStatus;
}
