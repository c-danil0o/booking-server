package com.komsije.booking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime startDate;
    @Column(nullable = false)
    private int days;
    @Column
    private Integer numberOfGuests;
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
