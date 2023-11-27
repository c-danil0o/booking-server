package com.komsije.booking.service;

import com.komsije.booking.model.Host;
import com.komsije.booking.model.Reservation;
import com.komsije.booking.model.ReservationStatus;
import com.komsije.booking.repository.HostRepository;
import com.komsije.booking.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation findOne(Long id) {
        return reservationRepository.findById(id).orElseGet(null);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getByReservationStatus(ReservationStatus reservationStatus){return  reservationRepository.findReservationsByReservationStatus(reservationStatus);}

    public Reservation save(Reservation accommodation) {
        return reservationRepository.save(accommodation);
    }

    public void remove(Long id) {
        reservationRepository.deleteById(id);
    }
}
