package com.komsije.booking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class Reservation {
    private Long id;
    private Date startDate;
    private int days;
    private double price;
    private Host host;
    private Guest guest;
    private Accommodation accommodation;
    private ReservationStatus reservationStatus;
}
