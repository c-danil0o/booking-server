package com.komsije.booking.dto;

import com.komsije.booking.model.ReservationStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReservationViewDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private String accommodationName;
    private String guestEmail;
    private String hostEmail;
    private ReservationStatus reservationStatus;
    private Integer numberOfGuests;

}
