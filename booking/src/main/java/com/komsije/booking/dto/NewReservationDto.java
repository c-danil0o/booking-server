package com.komsije.booking.dto;


import com.komsije.booking.model.ReservationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewReservationDto {
    private Long id;
    private LocalDateTime startDate;
    private int days;
    private double price;
    private ReservationStatus reservationStatus;
    private Long accommodationId;
    private String guestEmail;
    private String hostEmail;
    private Integer numberOfGuests;
}