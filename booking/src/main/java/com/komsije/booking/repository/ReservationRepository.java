package com.komsije.booking.repository;

import com.komsije.booking.model.Reservation;
import com.komsije.booking.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findReservationsByReservationStatus(ReservationStatus reservationStatus);
}
