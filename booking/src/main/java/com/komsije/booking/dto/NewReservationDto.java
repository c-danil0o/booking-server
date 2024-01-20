package com.komsije.booking.dto;


import com.komsije.booking.model.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewReservationDto {
    private Long id;
    private LocalDate startDate;
    private int days;
    private double price;
    private ReservationStatus reservationStatus;
    private Long accommodationId;
    private Long guestId;
    private Long hostId;
    private Integer numberOfGuests;
}