package com.komsije.booking.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Reservation {
    @Id
    private Long id;
    private Date startDate;
    private int days;
    private double price;

    @ManyToOne
    private Host host;

    @ManyToOne
    private Guest guest;

    @ManyToOne
    private Accommodation accommodation;
    private ReservationStatus reservationStatus;
}
