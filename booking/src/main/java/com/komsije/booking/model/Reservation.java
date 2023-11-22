package com.komsije.booking.model;

import jakarta.persistence.*;
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
    @Column(nullable = false)
    private Date startDate;
    @Column(nullable = false)
    private int days;
    @Column(nullable = false)
    private double price;

    @ManyToOne()
    private Host host;

    @ManyToOne
    private Guest guest;

    @ManyToOne
    private Accommodation accommodation;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;
}
