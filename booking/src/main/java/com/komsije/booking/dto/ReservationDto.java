package com.komsije.booking.dto;

import com.komsije.booking.model.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate dateCreated;
    private int days;
    private double price;
    private ReservationStatus reservationStatus;
    private Long accommodationId;
    private Long guestId;
    private Long hostId;
    private Integer numberOfGuests;


//    public ReservationDto(Reservation reservation){
//        this.id=reservation.getId();
//        this.startDate=reservation.getStartDate();
//        this.days=reservation.getDays();
//        this.price=reservation.getPrice();
//        this.reservationStatus=reservation.getReservationStatus();
//        this.accommodationId=reservation.getAccommodation().getId();
//    }

}
