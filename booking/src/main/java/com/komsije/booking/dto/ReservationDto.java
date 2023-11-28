package com.komsije.booking.dto;

import com.komsije.booking.model.*;
import lombok.*;

import java.util.Date;

@Data
public class ReservationDto {
    private Long id;
    private Date startDate;
    private int days;
    private double price;
    private ReservationStatus reservationStatus;
    private Long accommodationId;


//    public ReservationDto(Reservation reservation){
//        this.id=reservation.getId();
//        this.startDate=reservation.getStartDate();
//        this.days=reservation.getDays();
//        this.price=reservation.getPrice();
//        this.reservationStatus=reservation.getReservationStatus();
//        this.accommodationId=reservation.getAccommodation().getId();
//    }

}
