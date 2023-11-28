package com.komsije.booking.dto;

import com.komsije.booking.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Long id;
    private Date startDate;
    private int days;
    private double price;
    private ReservationStatus reservationStatus;
    private AccommodationDto accommodationDto;


//    public ReservationDto(Reservation reservation){
//        this.id=reservation.getId();
//        this.startDate=reservation.getStartDate();
//        this.days=reservation.getDays();
//        this.price=reservation.getPrice();
//        this.reservationStatus=reservation.getReservationStatus();
//        this.accommodationId=reservation.getAccommodation().getId();
//    }

}
