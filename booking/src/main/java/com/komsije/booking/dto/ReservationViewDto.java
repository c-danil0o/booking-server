package com.komsije.booking.dto;

import com.komsije.booking.model.ReservationStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReservationViewDto {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double price;
    private String accommodationName;
    private Long accommodationId;
    private Long guestId;
    private Long hostId;
    private String guestEmail;
    private String hostEmail;
    private ReservationStatus reservationStatus;
    private Integer numberOfGuests;

}
